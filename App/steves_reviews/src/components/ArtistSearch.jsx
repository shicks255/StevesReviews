import React, {useState} from 'react';

export default function ArtistSearch(props) {

    const [formValue, setFormValue] = useState("");

    console.log(props.history);

    function updateForm(e) {
        setFormValue(e.target.value);
    }

    function submitForm(e) {
        e.preventDefault();
        props.history.push(`/search/${formValue}`);
    }

    return (
        <div>
            <form onSubmit={submitForm}>
                <input type='text' onChange={(e) => updateForm(e)}/>
                <button className='button is-info' type='submit'>Search</button>
            </form>
        </div>
    )
}