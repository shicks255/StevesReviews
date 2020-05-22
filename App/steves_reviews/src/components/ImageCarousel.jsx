import React, {useEffect, useState} from 'react';
import NoImage from '../images/no-album-cover.png';

export default function ImageCarousel(props) {
    const images = props.images;
    const [imageIndex, setImageIndex] = useState(0);

    useEffect(() => {
        if (images.length > 0) {
            const firstIndex = images.findIndex(x => x.text == 'front' && x.size == 'main')
            setImageIndex(firstIndex);
        }
    }, [images]);

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
                     src={images.length > 0 ? images[imageIndex].url : NoImage}/>
            </figure>

            {
                images.map((x,y) => {
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