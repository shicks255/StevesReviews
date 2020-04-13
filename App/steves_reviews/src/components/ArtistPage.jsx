import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";

export default function ArtistPage(props) {

    const id = props.match.params.id;
    const [artist, setArtist] = useState({});
    const [albums, setAlbums] = useState([]);
    const [releaseTypes, setReleaseTypes] = useState([]);
    // const [fetchedAlbums, setFetchedAlbum] = useState([]);

    async function fetchData() {
        const artistData = await fetch(`/artist/${id}`);
        const artistJson = await artistData.json();
        setArtist(artistJson);
        console.log(artistJson);

        const albums = await fetch(`/album/artist/${id}`);
        const albumsJson = await albums.json();
        albumsJson.sort((a,b) => {
            return a.releaseDate.localeCompare(b.releaseDate);
        });

        const types = new Set();
        albumsJson.forEach((e) => types.add(e['primary-type']));
        setReleaseTypes([...types]);

        setAlbums(albumsJson);
        // setFetchedAlbum(albumsJson)
        console.log('the albums are...');
        console.log(albumsJson);
    }

    useEffect(() => {
        fetchData();
    }, []);

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
                            <img src="" />
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
            <div classNameName="column is-three-fifths">
                <br/><br/>
                {releaseTypes.map(rt => {
                    return (
                        <div>
                            <p>{rt}</p>
                            <table classNameName="table">
                                <tbody>
                                {albums.map(a => {
                                    return <tr>
                                        <td>
                                            <Link to={`/album/${a.id}`}>{a.title}</Link>
                                        </td>
                                        <td>{a.releaseDate.substring(0,4)}</td>
                                        <td></td>
                                    </tr>
                                })}
                                </tbody>
                            </table>
                        </div>
                    );
                })}

                <table classNameName="table">
                    <tbody>
                    <tr>
                        <th>Or add an album that's not yet in the database(if you are a <a href="@routes.UserController.registerHome()">registered user</a>).</th>
                    </tr>
                    </tbody>
                </table>
            </div>

        </div>
    );
}