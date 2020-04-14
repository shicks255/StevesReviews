import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";

export default function ArtistPage(props) {

    const id = props.match.params.id;
    const [artist, setArtist] = useState({});
    const [albums, setAlbums] = useState([]);
    const [releaseTypes, setReleaseTypes] = useState([]);
    const [loading, setLoading] = useState(true);
    // const [fetchedAlbums, setFetchedAlbum] = useState([]);

    async function fetchData() {
        const artistData = await fetch(`/artist/${id}`);
        const artistJson = await artistData.json();
        console.log(artistJson);
        setArtist(artistJson);
        console.log(artistJson);

        const albums = await fetch(`/album/artist/${id}`);
        const albumsJson = await albums.json();
        albumsJson.sort((a,b) => {
            return a['first-release-date'].localeCompare(b['first-release-date']);
        });

        const types = new Set();
        albumsJson.forEach((e) => types.add(e['primary-type']));
        setReleaseTypes([...types]);
        console.log(types);

        setAlbums(albumsJson);
        // setFetchedAlbum(albumsJson)
        console.log('the albums are...');
        console.log(albumsJson);
        setLoading(false);
    }

    useEffect(() => {
        fetchData();
    }, []);

    if (loading)
        return <div>loading</div>

    return (
        <div className="columns">
            <div className="column is-two-fifths">
                <div className="level">
                    <div className="level-item is-center">
                        <h1 className="title">{artist.name}</h1>
                    </div>
                </div>

                <div className="card">
                    <div className="card-image">
                        <div className="image">
                            {artist.images ? artist.images[0] ? <img src={artist.images[0][0]['#text']} /> : '' : ''}
                        </div>
                        <div className="card-content">
                            <div className="content">
                                {artist.disambiguation}
                                <br/>
                                <a href="" target="_blank">Read more on Last.fm</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="column is-three-fifths">
                <br/><br/>
                {releaseTypes.map(rt => {
                    return (
                        <div key={rt}>
                            <p>{rt}s</p>
                            <table className="table">
                                <tbody>
                                {albums.filter(x => x['primary-type'] == rt).map(a => {
                                    return <tr key={a.id}>
                                        <td>
                                            <img alt='image' src={
                                                a.images ? a.images[0] ? a.images[0][0].thumbnails.small : '' : ''} />
                                        </td>
                                        <td>
                                            <Link to={`/album/${a.id}`}>{a.title}</Link>
                                            <br/>
                                            {a['first-release-date'].substring(0,4)}
                                        </td>
                                    </tr>
                                })}
                                </tbody>
                            </table>
                        </div>
                    );
                })}

                {/*<table classNameName="table">*/}
                {/*    <tbody>*/}
                {/*    <tr>*/}
                {/*        <th>Or add an album that's not yet in the database(if you are a <a href="@routes.UserController.registerHome()">registered user</a>).</th>*/}
                {/*    </tr>*/}
                {/*    </tbody>*/}
                {/*</table>*/}
            </div>

        </div>
    );
}