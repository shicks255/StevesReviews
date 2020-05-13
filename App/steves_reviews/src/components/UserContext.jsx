import React, {useEffect, useState} from 'react';

const UserContext = React.createContext();
const UserContextProvider = (props) =>
{
    // let [authenticated, setAuthenticated] = useState(false);
    let [userCookie, setUserCookie] = useState('');

    useEffect(() => {
         // getUser();
         updateCookie();
    }, [userCookie]);

    // async function getUser() {
    //     if (userCookie.length == 0)
    //         return;
    //     const result = fetch('/auth/isLoggedIn', {
    //         headers: {
    //             'Authorization': 'Bearer ' + userCookie
    //         }
    //     });
    //
    //     result.then(resp => {
    //         if (resp.status === 200)
    //             setAuthenticated(true);
    //         else
    //             setAuthenticated(false);
    //     });
    // }

    function updateCookie() {
        const cookies = document.cookie;
        if (cookies.includes('sreviews=')) {
            const coocies = cookies.split(";");
            const cookieValue = coocies.find(x => x.includes('sreviews='))
            setUserCookie(cookieValue.split('=')[1]);
            console.log(userCookie);
        }
    }

    return (
        <UserContext.Provider value={{
            userCookie,
            // authenticated,
            updateCookie
        }}>
            {props.children}
        </UserContext.Provider>
    )
}

export { UserContextProvider, UserContext }