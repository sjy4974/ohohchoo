package com.ohohchoo.domain.weather.service;

import com.ohohchoo.domain.weather.dto.request.WeatherRequest;
import com.ohohchoo.domain.weather.dto.response.DateTime;
import com.ohohchoo.domain.weather.dto.response.LocationData;
import com.ohohchoo.domain.weather.dto.response.WeatherData;
import com.ohohchoo.domain.weather.dto.response.WeatherRangeData;
import com.ohohchoo.domain.weather.entity.Weather;
import com.ohohchoo.domain.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    // WeatherRequest 기준 +3일 까지의 시간별 온도 정보 리스트 반환
    public List<WeatherData> getWeather(WeatherRequest wthReq) {
        List<WeatherData> res = new ArrayList<>();
        // DB에 조회가능한 데이터가 있는지 확인
        if(!weatherRepository.existsByLocationCodeAndBaseDateAndBaseTime(wthReq.getLocationCode(), wthReq.getBaseDate(), wthReq.getBaseTime())) {
            // 없으면 업데이트 진행
            LocationData locationData = new LocationData(wthReq.getLocationCode(), wthReq.getNx(), wthReq.getNy());
            DateTime dateTime = new DateTime(wthReq.getBaseDate(), wthReq.getBaseTime());
            insertWeather(locationData, dateTime);
        }
        // DB에 있는 날씨 리스트 반환
        List<Weather> weathers = weatherRepository.findAllByLocationCodeAndBaseDateAndBaseTime(wthReq.getLocationCode(), wthReq.getBaseDate(), wthReq.getBaseTime());
        for(Weather weather : weathers){
            res.add(new WeatherData(weather.getFcstDate(), weather.getFcstTime(), weather.getPty(), weather.getSky(), weather.getTmp()));
        }
        return res;
    }

    // WeatherRequest 기준 최저 최고기온 정보 반환
    // 최저, 최고기온 데이터는 전 날 23시 기준으로 조회해야 함
    // 최저 기온은 새벽 6시 기준, 최고 기온은 15시 기준 날씨 정보에만 입력되어 있음
    public WeatherRangeData getWeatherRangeData(LocationData locData) {
        LocalDate dateNow = LocalDate.now();
        LocalDate yesterday = LocalDate.now().minusDays( 1 );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String currDate = dateNow.format(formatter);
        String baseDate = yesterday.format(formatter);

        // DB에 조회가능한 데이터가 있는지 확인
        if(!weatherRepository.existsByLocationCodeAndBaseDateAndBaseTime(locData.getLocationCode(), baseDate, "2300")) {
            // 없으면 업데이트 진행
            LocationData locationData = new LocationData(locData.getLocationCode(), locData.getNx(), locData.getNy());
            DateTime dateTime = new DateTime(baseDate, "2300");
            insertWeather(locationData, dateTime);
        }

        // 최저, 최고온도 저장하여 반환
        Weather tmnWeather = weatherRepository.findByLocationCodeAndBaseDateAndBaseTimeAndFcstDateAndFcstTime(
                locData.getLocationCode(), baseDate, "2300", currDate, "0600");
        Weather tmxWeather = weatherRepository.findByLocationCodeAndBaseDateAndBaseTimeAndFcstDateAndFcstTime(
                locData.getLocationCode(), baseDate, "2300", currDate, "1500");

        return new WeatherRangeData(tmnWeather.getTmn(), tmxWeather.getTmx());
    }


    // weather 정보 api로부터 받아와 DB에 삽입
    public void insertWeather(LocationData locData, DateTime dateTime) {
        String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";

        // git에 비공개 해야함!!!//
        String serviceKey = "vvjOiuxzurH8tt1NIBCtefiyDHnSAMFTa8khyKk%2BN8hXziKFerVlgdM%2F1NsMMsUG3LF3SiPGdR60nOqYJ%2BUH9g%3D%3D";

        Integer locationCode = locData.getLocationCode();
        String nx = locData.getNx();
        String ny = locData.getNy();
        String baseDate = dateTime.getBaseDate();
        String baseTime = dateTime.getBaseTime();
        String fcstDate = baseDate;
        String fcstTime = baseTime;

        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        try {
            // 요청 URL 생성
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
            urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*‘21년 6월 28일 발표*/
            urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /*06시 발표(정시단위) */
            urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
            urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점의 Y 좌표값*/

            URL url = new URL(urlBuilder.toString());

            // 요청 전송
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            String data = sb.toString();
            // 응답 수신완료

            // 응답 결과 JSON Parsing
            Integer pty = null;
            Integer sky = null;
            Double tmp = null;
            Double tmn = null;
            Double tmx = null;

            JSONObject jObject = new JSONObject(data);
            JSONObject response = jObject.getJSONObject("response");
            JSONObject body = response.getJSONObject("body");
            JSONObject items = body.getJSONObject("items");
            JSONArray jArray = items.getJSONArray("item");

            for(int i = 0; i < jArray.length(); i++) {
                JSONObject obj = jArray.getJSONObject(i);
                String objFcstDate = (String)obj.get("fcstDate");
                String objFcstTime = (String)obj.get("fcstTime");

                if(!objFcstDate.equals(fcstDate) || !objFcstTime.equals(fcstTime)) {
                    if(pty != null && sky != null && tmp != null ) {
                        Weather weather = new Weather(1, locationCode, baseDate, baseTime, fcstDate, fcstTime, pty, sky, tmp, tmn, tmx);
                        weatherRepository.save(weather);
                    }
                    fcstDate = objFcstDate;
                    fcstTime = objFcstTime;
                    pty = null;
                    sky = null;
                    tmp = null;
                    tmn = null;
                    tmx = null;
                }
                String category = (String)obj.get("category");
                switch(category){
                    case "PTY":
                        pty = Integer.parseInt((String)obj.get("fcstValue"));
                        break;
                    case "SKY":
                        sky = Integer.parseInt((String)obj.get("fcstValue"));
                        break;
                    case "TMP":
                        tmp = Double.parseDouble((String)obj.get("fcstValue"));
                        break;
                    case "TMN":
                        tmn = Double.parseDouble((String)obj.get("fcstValue"));
                        break;
                    case "TMX":
                        tmx = Double.parseDouble((String)obj.get("fcstValue"));
                        break;
                }
            }

        } catch (IOException e) {
            System.out.println("날씨 정보를 불러오는 중 오류 발생");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
