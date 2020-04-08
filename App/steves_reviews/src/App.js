import React from 'react';
import './App.css';
import Main from "./components/Main";
import {Route, Switch} from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import TopRated from "./components/TopRated";

export default function App() {

    return (
        <div className="App">
            <Header />
            <Switch>
                <Route path='/' component={Main} exact={true} />
                <Route path='/topRated' component={TopRated} />
            </Switch>
            <Footer/>
        </div>
    );
}