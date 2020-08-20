import React from 'react';
import './App.css';
import Main from "./components/Main";
import {Route, Switch} from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import TopRated from "./components/TopRated";
import ArtistSearch from "./components/ArtistSearch";
import ArtistPage from "./components/ArtistPage";
import ArtistSearchResults from "./components/ArtistSearchResults";
import AlbumPage from "./components/AlbumPage";
import Login from "./components/Login";
import MyAccount from "./components/MyAccount";
import AuthRoute from "./components/AuthRoute";
import UserPage from "./components/UserPage";

export default function App() {

    document.addEventListener('keyup', (e) => {
        if (e.keyCode === 27) {
            const modal = document.getElementById('myModal');
            if (modal)
                modal.classList.remove('is-active');

            const hamburger = document.getElementById('navbarLinks');
            if (hamburger)
                hamburger.classList.toggle('is-active');
            const $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);
            $navbarBurgers.forEach((e) => {
                e.classList.toggle('is-active');
            });
        }
    });

    document.addEventListener('click', (e) => {
        const nav = document.getElementById('navbar');

        if (e.target !== nav && !nav.contains(e.target)) {
            const hamburger = document.getElementById('navbarLinks');
            if (hamburger)
                hamburger.classList.remove('is-active');
            const $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);
            $navbarBurgers.forEach((e) => {
                e.classList.toggle('is-active');
            });
        }
    })

    return (
        <div className="App">
            <Header />
            <Switch>
                <Route path='/' component={Main} exact={true} />
                <Route path='/search' component={ArtistSearch}  exact={true}/>
                <Route path='/search/:search' component={ArtistSearchResults} />
                <Route path='/topRated' component={TopRated} />
                <Route path='/artist/:id' component={ArtistPage} />
                <Route path='/album/:id' component={AlbumPage} />
                <Route path='/login' component={Login} />
                <Route path='/user/:id' component={UserPage} />
                <AuthRoute path='/myAccount' component={MyAccount} />
            </Switch>
            <Footer/>
        </div>
    );
}