import React, {useEffect, useState} from 'react';
import NoImage from '../images/no-album-cover.png';
import {getCarouselArt} from "../utils/ImageRetriever";

export default function ImageCarousel(props) {
    let imagesAll = props.images;
    const [imageIndex, setImageIndex] = useState('');
    const [filtered, setFiltered] = useState([]);

    async function getImages() {
        const x = await getCarouselArt(imagesAll);
        setFiltered(x);
        setImageIndex(0);
    }

    useEffect(() => {
        getImages();
    }, [imagesAll]);

    function changeImage(e) {
        const index = e.target.id.substr(6);
        setImageIndex(parseInt(index));
    }

    if (imageIndex === '')
        return <div></div>

    return (
        <div>
            <figure className='image is-512x512'>
                <img width='512' height='512' key={imageIndex}
                     src={filtered.length > 0 ? filtered[imageIndex].url : NoImage}/>
            </figure>

            {
                filtered.map((x,y) => {
                    if (y === imageIndex)
                        return <i class='far fa-circle'></i>
                    return <i id={`image_${y}`}
                              onClick={changeImage}
                              style={{cursor: 'pointer'}}
                              className='fas fa-circle'></i>
                })
            }

        </div>
    )

}