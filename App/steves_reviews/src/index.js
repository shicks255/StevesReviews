import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from "react-router-dom";
import './index.css';
import 'bulma/css/bulma.min.css';
import 'bulma-modal-fx/dist/css/modal-fx.min.css';
import App from './App';
import { UserContextProvider } from "./components/UserContext";
import * as serviceWorker from './serviceWorker';

fetch('/api/visitor');

ReactDOM.render(
    <BrowserRouter>
        <UserContextProvider>
            <App />
        </UserContextProvider>
    </BrowserRouter>,
    document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
