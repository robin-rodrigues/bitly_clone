import React from "react";
import { RotatingLines } from "react-loader-spinner";

function Loader() {
    return (
        <div className="fixed inset-0 flex justify-center items-center bg-white">
            <RotatingLines
                visible={true}
                height="65"
                width="65"
                color="red"
                strokeWidth="5"
                animationDuration="0.75"
                ariaLabel="rotating-lines-loading"
            />
        </div>
    );
}

export default Loader;