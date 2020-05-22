import React, {useContext, useEffect, useState} from 'react';
import Review from "./Review";
import AddEditReview from "./AddEditReview";
import { UserContext } from "./UserContext";
import { Link } from 'react-router-dom';
import ImageCarousel from "./ImageCarousel";

export default function AlbumPage(props) {

    const id = props.match.params.id;
    const [loading, setLoading] = useState(true);
    const [data, setData] = useState({});
    const [discs, setDiscs] = useState([]);

    const context = useContext(UserContext);
    const cookie = context.cookie;

    useEffect(() => {
        getAlbumAndArtistAndImage();
    }, [cookie]);

    async function getAlbumAndArtistAndImage() {
        const result = await fetch(`/api/album/${id}`, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + cookie
            }
        });
        const data = await result.json();
        setData(data);

        let discs = new Set();
        data.album.tracks.forEach(x => discs.add(x.disc));
        setDiscs([...discs]);
        setLoading(false);
    }

    function getTimeStamp(millis) {
        const totalSeconds = (millis/1000);
        const minutes = Math.floor(totalSeconds/60);
        const left = Math.round(totalSeconds - (minutes*60));
        const seconds = (left<10) ? '0' + left : left;
        return minutes + ":" + seconds;
    }

    if (loading)
        return (
            <>
                <br/>
                <i className='fas fa-4x fa-stroopwafel fa-spin'></i>
                <br/>
                <br/>
            </>)

    return (
        <div>
            <nav className="breadcrumb" aria-label="breadcrumbs">
                <ul>
                    <li>
                        <Link to={`/artist/${data.artist.id}`}><b>{data.artist.name}</b></Link>
                    </li>
                    <li className="is-active">
                        <Link to="#" aria-current="page">{data.album.title}</Link>
                    </li>
                </ul>
            </nav>

            <div style={{paddingTop: 0}} className="columns">
                <div className="column is-one-third">
                    <ImageCarousel images={data.album.images} />

                    <table className="table is-hoverable is-narrow">
                        <tbody>
                        <tr className='subtitle is-4'>
                            <td colSpan="3" className='has-text-centered'><b>Tracks</b></td>
                        </tr>

                    {discs.map(d => {
                        return (
                            <>
                                <tr>
                                    <td colSpan={3}>{discs.length > 1 ? <b>Disc {d}</b> : ''}</td>
                                </tr>
                                {data.album.tracks.filter(tr => tr.disc == d).map(tr => {
                                    return <tr key={tr.id}>
                                        <td className='has-text-right'>{tr.number}.</td>
                                        <td>{tr.title}</td>
                                        <td>{getTimeStamp(tr.length)}</td>
                                    </tr>
                                })}
                                </>
                        )
                    })}
                        </tbody>
                    </table>
                </div>

                <div className="column is-two-thirds">
                    <table className="table is-hoverable is-fullwidth is-striped">
                        <tbody>
                        <tr>
                            <td colSpan="2" className='title is-4'>{data.album.title}</td>
                        </tr>
                        <tr>
                            <td colSpan="2">
                                <Link to={`/artist/${data.artist.id}`}><b>{data.artist.name}</b></Link>
                            </td>
                        </tr>
                        <tr>
                            <td style={{width: "50px"}}><b>Released</b>:</td>
                            <td>{data.album.releaseDate}</td>
                        </tr>
                        <tr>
                            <td><b>Rating:</b></td>
                            <td>{data.rating}</td>
                        </tr>
                        </tbody>
                    </table>
                    {context.loggedIn
                        ? <AddEditReview albumId={data.album.id} review={data.loggedInUserReview}/>
                        : 'Log in to leave a review'
                    }
                    {data.reviews.map(r => {
                        return <Review hideAlbum={true} key={r.id} album={data.album} review={r} />
                    })}
                </div>
            </div>
        </div>
    );
}