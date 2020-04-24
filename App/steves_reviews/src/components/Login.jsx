import React from 'react';

export default function Login() {

    async function login(e) {
        e.preventDefault();
        console.log(e.target.username.value);
        console.log(e.target.password.value);

        const body = {
            'username': e.target.username.value,
            'password': e.target.password.value,
        }

        console.log(JSON.stringify(body));

        const result = await fetch("/auth/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        });
        console.log(result);
        console.log(result.body);
        console.log('you have logged in :)');

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