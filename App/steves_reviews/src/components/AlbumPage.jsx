import React, {useEffect, useState} from 'react';
import Review from "./Review";

export default function AlbumPage(props) {

    const id = props.match.params.id;
    const [loading, setLoading] = useState(true);
    const [image, setImage] = useState('');
    const [data, setData] = useState({});
    const [discs, setDiscs] = useState([]);

    async function getAlbumAndArtistAndImage() {
        const result = await fetch(`/album/${id}`);
        const data = await result.json();
        setData(data);

        let discs = new Set();
        data.album.tracks.forEach(x => discs.add(x.disc));
        setDiscs([...discs]);

        const data2 = await fetch('http://coverartarchive.org/release-group/' + data.album.id);
        const json = await data2.json();
        setImage(json.images[0].image);

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
                        <a href={`/artist/${data.artist.id}`}><b>{data.artist.name}</b></a>
                    </li>
                    <li className="is-active">
                        <a href="#" aria-current="page">{data.album.title}</a>
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
                        </tbody>
                    </table>

                    {discs.map(d => {
                        return (
                            <table>
                                <tbody>
                                <tr><td colSpan={3}>{discs.length > 1 ? <b>Disc {d}</b> : ''}</td></tr>
                                {data.album.tracks.filter(tr => tr.disc == d).map(tr => {
                                    return <tr key={tr.id}>
                                        <td>{tr.number}</td>
                                        <td>{tr.title}</td>
                                        <td>{getTimeStamp(tr.length)}</td>
                                    </tr>
                                })}
                                </tbody>
                            </table>
                        )
                    })}
                </div>

                <div className="column is-two-thirds">
                    <table className="table is-narrow is-hoverable is-fullwidth">
                        <tbody>
                        <tr>
                            <td colSpan="2">{data.album.title}</td>
                        </tr>
                        <tr>
                            <td colSpan="2">
                                <a href={`/artist/${data.artist.id}`}><b>{data.artist.name}</b></a>
                            </td>
                        </tr>
                        <tr>
                            <td>Released:</td>
                            <td>{data.album.releaseDate}</td>
                        </tr>
                        <tr>
                            <td>Rating: {data.album.rating && data.album.rating}</td>
                        </tr>
                        </tbody>
                    </table>
                    {data.reviews.map(r => {
                        return <Review hideAlbum={true} key={r.id} album={data.album} review={r} />
                    })}
                </div>
            </div>
        </div>
    );
}