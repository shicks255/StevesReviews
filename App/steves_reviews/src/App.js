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

export default function App() {

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
            </Switch>
            <Footer/>
        </div>
    );
}