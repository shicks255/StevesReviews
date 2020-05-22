import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";
import {getCoverArtThumb} from "../utils/ImageRetriever";

export default function ArtistPage(props) {

    const id = props.match.params.id;
    const [artist, setArtist] = useState({});
    const [albums, setAlbums] = useState([]);
    const [releaseTypes, setReleaseTypes] = useState([]);
    const [loading, setLoading] = useState(true);

    async function fetchData() {
        const artistData = await fetch(`/api/artist/${id}`);
        const artistJson = await artistData.json();
        setArtist(artistJson);

        const albums = await fetch(`/api/album/artist/${id}`);
        let albumsJson = await albums.json();

        const types = new Set();
        albumsJson.forEach((e) => types.add(e.type));
        setReleaseTypes([...types]);

        setAlbums(albumsJson);
        setLoading(false);
    }

    useEffect(() => {
        fetchData();
    }, []);

    if (loading)
        return (
            <>
                <br/>
                <i className='fas fa-4x fa-stroopwafel fa-spin'></i>
                <br/>
                <br/>
            </>)

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
                            <figure className="image is-512x512">
                                {artist.images ? artist.images[0] ? <img src={artist.images[0].url} /> : '' : ''}
                            </figure>
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
                        <div key={rt} className='has-text-left'>
                            <p><b>{rt}s</b></p>
                            <table className="table">
                                <tbody>
                                {albums.filter(x => x.type == rt).map(a => {
                                    return <AlbumLine key={a.id} album={a} />
                                })}
                                </tbody>
                            </table>
                        </div>
                    );
                })}

            </div>
        </div>
    );
}

function AlbumLine(props) {

    const album = props.album;
    const [image, setImage] = useState('');

    async function getImage() {
        const x = await getCoverArtThumb(album);
        setImage(x);
    }

    useEffect(() => {
        getImage();
    }, [])

    return (
        <tr key={album.id}>
            <td>
                <figure className="image is-128x128">
                    <Link to={`/album/${album.id}`}>
                        <img alt='image' src={image} />
                    </Link>
                </figure>
            </td>
            <td>
                <Link to={`/album/${album.id}`}>{album.title}</Link>
                <br/>
                {album.releaseDate ? album.releaseDate.substring(0,4) : ''}
            </td>
            <td>
                <b>{album.rating && album.rating}</b>
            </td>
        </tr>
    )

}