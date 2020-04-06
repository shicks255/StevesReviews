import React from 'react';
import Logo from '../images/StevesMusicReviews.png';

export default function Header() {

    return (
        <header>
            <nav className="navbar is-light" role="navigation" aria-label="main navigation">
                <div className="navbar-brand">
                    <div className="navbar-item">
                        <a href="@controllers.routes.HomeController.index()">
                            <figure className="image">
                                <img src={Logo} />
                            </figure>
                        </a>
                    </div>

                    <a role="button" className="navbar-burger" aria-label="menu" aria-expanded="false"
                       data-target="navbarLinks">
                        <span aria-hidden="true"></span>
                        <span aria-hidden="true"></span>
                        <span aria-hidden="true"></span>
                    </a>
                </div>
                <div id="navbarLinks" className="navbar-menu">
                    <div className="navbar-start is-light">
                        <div className="navbar-item">
                            <a href="@controllers.routes.ArtistController.artistSearchHome()">Search for artist</a>
                        </div>
                        <div className="navbar-item">
                            <a href="@controllers.routes.ReviewController.topRated()">Top Rated</a>
                        </div>
                        {/*@if(session.get("userId").nonEmpty) {*/}
                        <div className="navbar-item">
                            <a href="@controllers.routes.UserController.userHome()">My Account</a>
                        </div>
                        < div className="navbar-item">
                            <a href="@controllers.routes.UserController.logout()">Logout</a>
                        </div>
                        {/*}else{*/}
                        <div className="navbar-item">
                            <a href="@controllers.routes.UserController.loginHome()">Login/Register</a>
                        </div>
                        // }
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