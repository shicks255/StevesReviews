import React, {useState} from 'react';
import TimedModal from "./TimedModal";

export default function Login(props) {

    const [tab, setTab] = useState('login')

    function setNewTab(e) {
        const newTab = e.target.text.toLowerCase();
        setTab(newTab);
    }

    async function login(e) {
        e.preventDefault();
        const body = {
            'username': e.target.username.value,
            'password': e.target.password.value,
        }

        const result = await fetch("/api/auth/login", {
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
            if (result.status === 403) {
                const modal = document.getElementsByClassName('modal')[0];
                const body = document.getElementById('message-body');
                const messageHeader = document.getElementById('message-article');

                messageHeader.classList.add('is-danger');
                messageHeader.classList.remove('is-info');
                body.innerText = 'The username and password combination is incorrect.';
                modal.classList.add('is-active');
            }
        }
    }

    async function register(e) {
        e.preventDefault();
        const body = {
            'username': e.target.username.value,
            'password': e.target.password.value,
        }

        const result = await fetch("/api/user/register", {
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

            const modal = document.getElementsByClassName('modal')[0];
            const body = document.getElementById('message-body');
            const messageHeader = document.getElementById('message-article');
            messageHeader.classList.remove('is-danger');
            messageHeader.classList.add('is-info');
            body.innerText = response;
            modal.classList.add('is-active');
        }
        else {
            const modal = document.getElementsByClassName('modal')[0];
            const body = document.getElementById('message-body');
            const messageHeader = document.getElementById('message-article');
            messageHeader.classList.add('is-danger');
            messageHeader.classList.remove('is-info');
            body.innerText = response;
            modal.classList.add('is-active');
        }
    }

    return (
        <>
            <TimedModal timeout={5000}>
                <article id='message-article' className='message is-danger'>
                    <div id='message-body' className='message-body'>
                        The username and password combination is incorrect.
                    </div>
                </article>
            </TimedModal>
            <div className='columns'>
                <div className='column is-half is is-offset-one-quarter'>
                    <div className='tabs is-boxed'>
                        <ul>
                            <li className={tab === 'login' ? 'is-active' : ''}><a href='' onClick={setNewTab}>Login</a></li>
                            <li className={tab === 'register' ? 'is-active' : ''}><a href='' onClick={setNewTab}>Register</a></li>
                        </ul>
                    </div>

                    {tab === 'login' ? (
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
                    ) : (
                        <article className='message is-large'>
                            <div className='message-header'>
                                Register
                            </div>
                            <div className='message-body'>
                                <form onSubmit={register}>
                                    <table>
                                        <tbody>
                                        <tr>
                                            <td>
                                                <label>New Username</label>
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
                                                <button className='button is-link' type='submit'>Register</button>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </form>
                            </div>
                        </article>
                    )}
                </div>
            </div>
        </>
    )
}
