import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";

export default function ArtistSearchResults(props) {

    const search = props.match.params.search;
    const [artists, setArtists] = useState([]);
    const [loading, setLoading] = useState(true);

    async function searchArtists() {
        const data = await fetch(`/artist/search/${search}`);
        const artistResults = await data.json();
        setArtists(artistResults);
        setLoading(false);
    }

    useEffect(() => {
        searchArtists();
    }, []);

    if (loading)
        return <div>loading</div>

    return (
        <div>
            {search}
            <table>
                <tbody>
                <tr>
                    <td>Name</td>
                    <td></td>
                    <td>Formed</td>
                    <td>Tags</td>
                </tr>

                {artists.map(a => {
                    return (
                        <tr key={a.id}>
                            <td>
                                <Link to={`/artist/${a.id}`} >
                                    {a.name}
                                </Link>
                                <br/>
                            </td>
                            <td>{a.disambiguation}</td>
                            <td>
                                {(a['begin-area']) ? a['begin-area'].name + ',' : ''}
                                {a.country}
                                <br/>
                                {(a['life-span']) ? a['life-span'].begin : ''}
                            </td>
                            <td>
                                {a.tags ? a.tags.map(t => <span key={t.name}>{t.name}</span>) : ''}
                            </td>
                        </tr>
                    )
                })}

                </tbody>
            </table>

        </div>
    );

}