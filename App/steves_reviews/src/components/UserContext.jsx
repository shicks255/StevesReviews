import React, {useEffect, useState} from 'react';

const UserContext = React.createContext();
const UserContextProvider = (props) =>
{
    const [cookie, setCookie] = useState('');
    const [loggedIn, setLoggedIn] = useState(false);

    useEffect(() => {
        isLoggedIn();
    }, []);

    async function isLoggedIn() {
        const cookies = document.cookie;
        if (cookies.includes('sreviews=')) {
            const coocies = cookies.split(";");
            const cookieValue = coocies.find(x => x.includes('sreviews='))
            const cookie = cookieValue.split('=')[1];
            setCookie(cookie);

            console.log('calling isLoggedIn')
            const result = await fetch('/api/auth/isLoggedIn', {
                headers: {
                    'Authorization' : 'Bearer ' + cookie
                }
            });
            if (result.status === 200) {
                setLoggedIn(true);
                return true;
            }
            else
                document.cookie = "sreviews=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
        }
        return false;
    }

    return (
        <UserContext.Provider value={{
            cookie,
            loggedIn
        }}>
            {props.children}
        </UserContext.Provider>
    )
}

export { UserContextProvider, UserContext }