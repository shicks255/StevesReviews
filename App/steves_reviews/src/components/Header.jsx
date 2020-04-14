import React from 'react';
import Logo from '../images/StevesMusicReviews.png';

export default function Header() {

    return (
        <header>
            <nav className="navbar is-light" role="navigation" aria-label="main navigation">
                <div className="navbar-brand">
                    <div className="navbar-item">
                        <a href="/">
                            <figure className="image">
                                <img alt="stevesreviews.net" src={Logo} />
                            </figure>
                        </a>
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
                            <a href="/search">Search for artist</a>
                        </div>
                        <div className="navbar-item">
                            <a href="/topRated">Top Rated</a>
                        </div>
                        {/*@if(session.get("userId").nonEmpty) {*/}
                        <div className="navbar-item">
                            <a href="@controllers.routes.UserController.userHome()">My Account</a>
                        </div>
                        < div className="navbar-item">
                            <a href="@controllers.routes.UserController.logout()">Logout</a>
                        </div>
                        <div className="navbar-item">
                            <a href="@controllers.routes.UserController.loginHome()">Login/Register</a>
                        </div>
                    </div>
                    <div className="navbar-end">
                        <div className="navbar-item">
                            ...when words fail, music speaks
                        </div>
                    </div>
                </div>

                <hr className="navbar-divider" />
            </nav>
        </header>
    );
}