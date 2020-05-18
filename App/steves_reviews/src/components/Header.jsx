import React, {useContext, useEffect, useState} from 'react';
import { UserContext } from "./UserContext";
import { Link } from 'react-router-dom';
import Logo from '../images/StevesMusicReviews.png';

export default function Header() {

    const context = useContext(UserContext);

    document.addEventListener('DOMContentLoaded', function ()
    {
        var $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);
        if ($navbarBurgers.length > 0) {
            $navbarBurgers.forEach(function (el)
            {
                el.addEventListener('click', function ()
                {
                    var target = el.dataset.target;
                    var $target = document.getElementById(target);

                    el.classList.toggle('is-active');
                    $target.classList.toggle('is-active');
                });
            });
        }
    });

    function logout()
    {
        document.cookie = "sreviews=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
        window.location = "/";
    }

    return (
        <header>
            <nav className="navbar is-light" role="navigation" aria-label="main navigation">
                <div className="navbar-brand">
                    <div className="navbar-item">
                        <Link to="/">
                            <figure className="image">
                                <img alt="stevesreviews.net" src={Logo}/>
                            </figure>
                        </Link>
                    </div>

                    <button className="navbar-burger" aria-label="menu" aria-expanded="false"
                            data-target="navbarLinks">
                        <span aria-hidden="true"></span>
                        <span aria-hidden="true"></span>
                        <span aria-hidden="true"></span>
                    </button>
                </div>
                <div id="navbarLinks" className="navbar-menu">
                    <div className="navbar-start is-light">
                        <div className="navbar-item">
                            <Link to="/search">Search for artist</Link>
                        </div>
                        <div className="navbar-item">
                            <Link to="/topRated">Top Rated</Link>
                        </div>
                        {/*@if(session.get("userId").nonEmpty) {*/}
                        {!context.loggedIn &&
                        <div className="navbar-item">
                            <Link to="/login">Login/Register</Link>
                        </div>
                        }
                        {context.loggedIn &&
                        <>
                            <div className="navbar-item">
                                {/*<a onClick={ensureLoggedIn} >My Account</a>*/}
                                <Link to={'/myAccount'}>My Account</Link>
                            </div>
                            <div className="navbar-item">
                                <button className='button is-link' onClick={logout}>Logout</button>
                            </div>
                        </>
                        }
                    </div>
                    <div className="navbar-end">
                        <div className="navbar-item">
                            ...when words fail, music speaks
                        </div>
                    </div>
                </div>

                <hr className="navbar-divider"/>
            </nav>
        </header>
    );
}
