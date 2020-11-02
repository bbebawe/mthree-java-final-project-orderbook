import React from "react";

export default function Button(props) {
    //console.log(props.disable)
  return (
    <button
      id={props.id}
      type="button"
      className="btn btn-secondary"
      disabled={props.disable}
      onClick={props.clickHandler}
    >
      {props.name}
    </button>
  );
}
