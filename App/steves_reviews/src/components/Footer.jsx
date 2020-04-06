import React from 'react';


export default function Footer() {

    return (
        <footer className="footer">
            <div className="content has-text-centered">
                <a href="http://www.stevenmhicks.com">Steven Hicks</a>
                <a href="@controllers.routes.HomeController.about()">About</a>

                <br/>
                &copy;Steven M Hicks - License
            </div>
        </footer>
    );
}