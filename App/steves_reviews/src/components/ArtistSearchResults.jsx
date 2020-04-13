import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";

export default function ArtistSearchResults(props) {

    const search = props.match.params.search;
    const [artists, setArtists] = useState([]);

    async function searchArtists() {
        const data = await fetch(`/artist/search/${search}`);
        const artistResults = await data.json();
        console.log(artistResults);
        setArtists(artistResults);
    }

    useEffect(() => {
        searchArtists();
    }, []);

    return (
        <div>
            {search}


            <table>
                <tbody>
                <tr>
                    <td></td>
                    <td>Formed</td>
                    <td></td>
                </tr>

                {artists.map(a => {
                    console.log(a);
                    return (
                        <tr>
                            <td>
                                <Link to={`/artist/${a.id}`} >
                                    {a.name}
                                </Link>
                                <br/>
                                {a.disambiguation}
                            </td>
                            <td>
                                {(a['begin-area']) ? a['begin-area'].name + ',' : ''}
                                {a.country}
                                <br/>
                                {(a['life-span']) ? a['life-span'].begin : ''}
                            </td>
                        </tr>
                    )
                })};

                </tbody>
            </table>

        </div>
    );

}