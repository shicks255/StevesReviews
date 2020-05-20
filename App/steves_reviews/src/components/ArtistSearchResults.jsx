import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";

export default function ArtistSearchResults(props) {

    const search = props.match.params.search;
    const [artists, setArtists] = useState([]);
    const [loading, setLoading] = useState(true);

    async function searchArtists() {
        const data = await fetch(`/api/artist/search/${search}`);
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
        <div className='has-text-left'>
            Searching for... {search}
            <table className='table is-striped is-hoverable'>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Formed</th>
                    <th>Tags</th>
                </tr>
                </thead>

                <tbody>
                {artists.map(a => {
                    return (
                        <tr key={a.id}>
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
                            <td>
                                {a.tags ? a.tags.map(t =>
                                    <span className='tag is-rounded is-normal is-info is-light' key={t.name}>{t.name}</span>
                                ): ''}
                            </td>
                        </tr>
                    )
                })}

                </tbody>
            </table>

        </div>
    );

}