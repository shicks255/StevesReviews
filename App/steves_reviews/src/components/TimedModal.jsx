import React, {useEffect} from 'react';

function closeModal() {
    const modal = document.getElementById('myModal');
    modal.classList.remove('is-active');
}

export default function TimedModal(props) {

    useEffect(() => {
        setTimeout(() => {
            closeModal();
        }, props.timeout);
    })

    return (
        <div id='myModal' className='modal modal-fx-fadeInScale'>
            <div onClick={closeModal} className='modal-background'></div>
            <div className='modal-content'>
                {props.children}
            </div>
            <button className='modal-close is-medium' onClick={closeModal} aria-label='close'></button>
        </div>
    )
}