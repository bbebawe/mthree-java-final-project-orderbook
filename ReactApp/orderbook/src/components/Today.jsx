import React from "react";

export default function Today(props) {
  return (
    <div className="text-left">
      <h4>TODAYS ACTIVITY</h4>
      <h5 className="text-left-right">
        <span className="left-text">Order Volume</span>
        <span className="byline">{props.info.orderVolume}</span>
      </h5>
      <h5 className="text-left-right">
        <span className="left-text">Trade Volume</span>
        <span className="byline">{props.info.tradeVolume}</span>
      </h5>
      <h5 className="text-left-right">
        <span className="left-text">Latest Match</span>
        <span className="byline">{props.info.lastMatch}</span>
      </h5>
      <h5 className="text-left-right">
        <span className="left-text">Time</span>
        <span className="byline">{props.info.time}</span>
      </h5>
      <h5 className="text-left-right">
        <span className="left-text">Spread</span>
        <span className="byline">{props.info.spread}</span>
      </h5>
    </div>
  );
}
