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
        <div className='columns'>
            <div className='column is-half is is-offset-one-quarter'>
                <article className='message is-large'>
                    <div className='message-header'>
                        Login
                    </div>
                    <div className='message-body'>
                        <form onSubmit={login}>
                            <table>
                                <tbody>
                                <tr>
                                    <td>
                                        <label>Username</label>
                                        <br/>
                                        <input type='text' id='username'></input>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label>Password</label>
                                        <br/>
                                        <input type='password' id='password'></input>
                                    </td>
                                </tr>

                                <tr>
                                    <td>
                                        <br/>
                                        <button className='button is-link' type='submit'>Login</button>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </article>
            </div>
        </div>
    )

}
