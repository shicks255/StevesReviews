import React, {useEffect, useState} from 'react';
import Review from "./Review";

export default function AlbumPage(props) {

    const id = props.match.params.id;
    const [album, setAlbum] = useState({});
    const [loading, setLoading] = useState(true);
    const [credit, setCredit] = useState({});
    const [image, setImage] = useState('');

    async function getAlbumAndArtistAndImage() {
        const data = await fetch(`/album/${id}`);
        const album = await data.json();
        setAlbum(album);

        const data2 = await fetch('http://coverartarchive.org/release-group/' + album.id);
        const json = await data2.json();
        setImage(json.images[0].image);

        const credit = album['artist-credit'][0];
        setCredit(credit);
        setLoading(false);
    }

    useEffect(() => {
        getAlbumAndArtistAndImage();
    }, []);

    function getTimeStamp(millis) {
        const totalSeconds = (millis/1000);
        const minutes = Math.floor(totalSeconds/60);
        const left = totalSeconds - (minutes*60);
        const seconds = Math.round(left);

        return minutes + ":" + seconds;
    }

    if (loading)
        return <div>loading</div>

    return (
        <div>
            <nav className="breadcrumb" aria-label="breadcrumbs">
                <ul>
                    <li>
                        <a href={`/artist/${credit.artist.id}`}><b>{credit.name}</b></a>
                    </li>
                    <li className="is-active">
                        <a href="#" aria-current="page">{album.title}</a>
                    </li>
                </ul>
            </nav>

            <br/>

            <div className="columns">
                <div className="column is-one-third">
                    <figure className='image is-512x512'>
                        <img src={image}/>
                    </figure>

                    <table className="table is-hoverable is-narrow">
                        <tbody>
                        <tr>
                            <td colSpan="3"><b>Tracks</b></td>
                        </tr>

                        {album.tracks[0].map(tr => {
                            return <tr key={tr.id}>
                                <td>{tr.number}</td>
                                <td>{tr.title}</td>
                                <td>{getTimeStamp(tr.length)}</td>
                            </tr>
                        })}
                        </tbody>
                    </table>
                </div>

                <div className="column is-two-thirds">
                    <table className="table is-narrow is-hoverable is-fullwidth">
                        <tbody>
                        <tr>
                            <td colSpan="2">{album.title}</td>
                        </tr>
                        <tr>
                            <td colSpan="2">
                                <a href={`/artist/${credit.artist.id}`}><b>{credit.name}</b></a>
                            </td>
                        </tr>
                        <tr>
                            <td>Released:</td>
                            <td>{album['first-release-date']}</td>
                        </tr>
                        <tr>
                            <td>Rating:</td>
                        </tr>
                        </tbody>
                    </table>
                    {album.reviews.map(r => {
                        return <Review hideAlbum={true} key={r.id} album={album} review={r} />
                    })}
                </div>
            </div>
        </div>
    );
}