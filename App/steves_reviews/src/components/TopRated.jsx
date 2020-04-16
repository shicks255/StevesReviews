import React, {useEffect, useState} from 'react';
import NoImage from '../images/no-album-cover.png';

export default function TopRated() {

    const [albumsAndRatings, setAlbumsAndRatings] = useState([]);

    async function loadAlbumsAndRatings() {
        const data = await fetch('/album/topRated')
        const albumsAndRatings = await data.json();
        console.log(albumsAndRatings);
        setAlbumsAndRatings(albumsAndRatings);
    }

    useEffect(() => {
        loadAlbumsAndRatings();
    }, []);

    if (albumsAndRatings.length === 0)
        return "";

    return (
        <table className="table">
            <tbody>
            {albumsAndRatings.map((ar, index) => {
                return <AlbumLine rank={index} ar={ar}/>
            })};
            </tbody>
        </table>
    )
}

function AlbumLine(props) {

    const rank = props.rank;
    const ar = props.ar;
    const [image, setImage] = useState(NoImage);

    async function loadImage() {
        const data2 = await fetch('http://coverartarchive.org/release-group/' + ar.album.id);
        const json = await data2.json();
        setImage(json.images[0].image);
    }

    useEffect(() => {
        loadImage();
    }, []);

    return (
        <tr>
            <td>{rank + 1}</td>
            <td>
                <a href={`/artist/${ar.album['artist-credit'][0].artist.id}`}>
                    <b>{ar.album['artist-credit'][0].name}</b>
                </a>
                <figure className="image is-128x128">
                    <a href={`album/${ar.album.id}`} >
                        <img alt='images' src={image}/>
                    </a>
                </figure>
                <a href={`/album/${ar.album.id}`}>{ar.album.title}</a>
            </td>
            <td>{ar.averageRating}</td>
            <td>{ar.review.content}</td>
        </tr>
    );
}