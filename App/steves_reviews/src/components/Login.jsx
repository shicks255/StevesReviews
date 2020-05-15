import React, {useContext} from 'react';
import {UserContext} from "./UserContext";

export default function Login(props) {

    async function login(e) {
        e.preventDefault();
        const body = {
            'username': e.target.username.value,
            'password': e.target.password.value,
        }

        const result = await fetch("/auth/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        });
        const response = await result.text();

        if (result.status === 200) {
            const cookie = response;
            let date = new Date();
            date.setTime(date.getTime()+(30*24*60*60*1000));
            document.cookie = "sreviews=" + cookie + "; expires=" + date.toGMTString();
            if (props.continueTo)
                window.location = props.continueTo;
            window.location = '/';
        }
        else {
            //if no good login do a modal saying invalid usern
        }

    }

    return (
        <div>
            <form onSubmit={login}>
                <input type='text' id='username'></input>
                <label>Username</label>
                <br/>
                <input type='text' id='password'></input>
                <label>Password</label>

                <button type='submit'>Login</button>
            </form>
        </div>
    )

}