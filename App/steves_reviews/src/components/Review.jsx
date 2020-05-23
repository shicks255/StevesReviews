import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import {getCoverArtThumb} from "../utils/ImageRetriever";

export default function Review(props) {
    const {reviewDto, hideAlbum} = props;
    const [image, setImage] = useState('');

    async function getImage() {
        const x = await getCoverArtThumb(reviewDto.review.album);
        setImage(x);
    }

    useEffect(() => {
        getImage();
    }, []);

    function toggleCollapse(e) {
        e.preventDefault();
        e.target.classList.toggle('fa-rotate-270');

        const textarea = document.getElementById(`reviewContent_${reviewDto.review.id}`);
        textarea.classList.toggle('collapsed');
    }

    let imageStuff = '';
    let contentColumnClass = 'is-full';
    if (!hideAlbum) {
        contentColumnClass = 'is-three-quarters';
        imageStuff =
            <div className="column is-one-quarter has-text-left">
                <Link to={`/artist/${reviewDto.artist.id}`}><b>{reviewDto.artist.name}</b></Link>
                <figure className="image is-128x128">
                    <Link to={`/album/${reviewDto.review.album.id}`}>
                        <img alt='myimage' src={image}/>
                    </Link>
                </figure>
                <Link to={`/album/${reviewDto.review.album.id}`}>{reviewDto.review.album.title}</Link>
            </div>
    }

    return(
        <div className="box">
            <div className="columns noPad">
                {imageStuff}
                <div className={`column ${contentColumnClass}`}>
                    <div className='has-text-left'>
                        <Link to={`user/${reviewDto.review.user.id}`}>{reviewDto.review.user.username}</Link>
                        <br/>
                        <span className={`tag ${reviewDto.colorClass ? reviewDto.colorClass : reviewDto.review.colorClass}`}>
                            Rating: {reviewDto.rating ? reviewDto.rating : reviewDto.review.rating}
                        </span>
                        <br/>
                        {reviewDto.review.addedOn}
                    </div>
                    <br/>
                    <div>
                        <span className="icon is-pulled-left" id={`reviewArrow_${reviewDto.review.id}`} onClick={toggleCollapse} style={{cursor: 'pointer'}}>
                            <i className="fas fa-arrow-down"></i>
                        </span>
                        <br/>
                        <div className="reviewContent has-text-justified" id={`reviewContent_${reviewDto.review.id}`}>
                            {reviewDto.review.content}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );

}