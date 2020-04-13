import React, {useEffect, useState} from 'react';

export default function AlbumPage(props) {

    const id = props.match.params.id;
    const [album, setAlbum] = useState({});
    const [loading, setLoading] = useState(true);
    const [credit, setCredit] = useState({});

    async function getAlbum() {
        console.log(id);
        const data = await fetch(`/album/${id}`);
        const album = await data.json();
        console.log(album);
        setAlbum(album);
        const credit = album['artist-credit'][0];
        setCredit(credit);
        setLoading(false);
    }

    useEffect(() => {
        getAlbum();
    },[]);

    function getTimeStamp(millis) {
        const minutes = Math.floor((millis/1000)/60);
        const left = millis - (minutes*1000*60);
        const seconds = left/1000;

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
                    <img src={album.images[0][0].image}/>

                    <table className="table is-hoverable is-narrow">
                        <tbody>
                        <tr>
                            <td colSpan="3"><b>Tracks</b></td>
                        </tr>

                        {album.tracks[0].map(tr => {
                            return <tr>
                                    <td>{tr.number}</td>
                                    <td>{tr.title}</td>
                                    <td>{getTimeStamp(tr.length)}</td>
                                </tr>
                        })}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}