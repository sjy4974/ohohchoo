import React from "react";
import { Link } from "react-router-dom";

export default function Nav() {
  return (
    <nav>
      <Link to='/location'>
        <img
          alt='Netflix logo'
          src='https://upload.wikimedia.org/wikipedia/commons/thumb/0/08/Netflix_2015_logo.svg/170px-Netflix_2015_logo.svg.png'
          className='nav__logo'
        />
      </Link>

      <Link to='/login'>
        <img
          alt='User logged'
          src='public/public_assets/login-btn.png'
          className='nav__avatar'
        />
      </Link>

    </nav>
  );
}
