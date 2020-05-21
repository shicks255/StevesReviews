import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import {getCoverArtThumb} from "../utils/ImageRetriever";

export default function Review(props) {
    const {review, album, artist, rating, colorClass} = props;
    const [image, setImage] = useState('');

    async function getImage() {
        const x = await getCoverArtThumb(album);
        setImage(x);
    }

    useEffect(() => {
        getImage();
    }, []);

    function toggleCollapse(e) {
        e.preventDefault();
        e.target.classList.toggle('fa-rotate-270');

        const textarea = document.getElementById(`reviewContent_${review.id}`);
        textarea.classList.toggle('collapsed');
    }

    let imageStuff = '';
    let contentColumnClass = 'is-full';
    if (!props.hideAlbum) {
        contentColumnClass = 'is-three-quarters';
        imageStuff =
            <div className="column is-one-quarter has-text-left">
                <Link to={`/artist/${artist.id}`}><b>{artist.name}</b></Link>
                <figure className="image is-128x128">
                    <Link to={`/album/${album.id}`}>
                        <img alt='myimage' src={image}/>
                    </Link>
                </figure>
                <Link to={`/album/${album.id}`}>{album.title}</Link>
            </div>
    }

    return(
        <div className="box">
            <div className="columns noPad">
                {imageStuff}
                <div className={`column ${contentColumnClass}`}>
                    <div className='has-text-left'>
                        <Link to={`user/${review.user.id}`}>{review.user.username}</Link>
                        <br/>
                        <span className={`tag ${colorClass ? colorClass : review.colorClass}`}>
                            Rating: {rating ? rating : review.rating}
                        </span>
                        <br/>
                        {review.addedOn}
                    </div>
                    <br/>
                    <div>
                        <span className="icon is-pulled-left" id={`reviewArrow_${review.id}`} onClick={toggleCollapse} style={{cursor: 'pointer'}}>
                            <i className="fas fa-arrow-down"></i>
                        </span>
                        <br/>
                        <div className="reviewContent has-text-justified" id={`reviewContent_${review.id}`}>
                            {review.content}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );

}