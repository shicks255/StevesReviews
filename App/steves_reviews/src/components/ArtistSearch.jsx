import React, {useState} from 'react';
import {Redirect} from "react-router-dom";

export default function ArtistSearch(props) {

    const [formValue, setFormValue] = useState("");
    const [doRedirect, setDoRedirect] = useState(false);

    console.log(props.history);

    function updateForm(e) {
        setFormValue(e.target.value);
    }

    function submitForm(e) {
        e.preventDefault();
        console.log('hi');
        setDoRedirect(true);
        props.history.push(`/search/${formValue}`);
    }

    // if (doRedirect)
    //     return <Redirect
    //         push={true}
    //         to={`/search/${formValue}`} />

    return (
        <div>
            <form onSubmit={submitForm}>
                <input type='text' onChange={(e) => updateForm(e)}/>
                <button type='submit'>Search</button>
            </form>
        </div>
    )
}