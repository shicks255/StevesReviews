import React, {useEffect, useState} from 'react';
import { Route, Redirect } from 'react-router-dom';
import Login from "./Login";

export default function AuthRoute({component: Component, ...rest}) {

    const [status, setStatus] = useState(0);

    useEffect(() => {
        ensureLoggedIn();
    }, []);

    function ensureLoggedIn() {
        console.log('ensuring logged in from AuthenticatedRoute');
        const cookies = document.cookie;
        let cookie;
        if (cookies.includes('sreviews=')) {
            const coocies = cookies.split(";");
            const cookieValue = coocies.find(x => x.includes('sreviews='))
            cookie = cookieValue.split('=')[1];
            rest.cookie = cookie;
        }
        const result = fetch('/api/auth/isLoggedIn', {
            headers: {
                'Authorization' : 'Bearer ' + cookie
            }
        });

        result.then(resp => {
            console.log(rest);
            console.log(resp);
            setStatus(resp.status);
        })
    }

    if (status < 1)
        return '';

    return (
        <Route {...rest}
               render={props =>
                   status === 200 ?
            <Component {...props} /> :
                       <Login continueTo={rest.path} to={'/login'} />
        }
        />
    );
}