import React from "react";
import { Row, Col } from "react-bootstrap";

export default function Title() {
  return (
    <>
      <Row className="pt-3 pb-0">
        <Col>
          <h1 className="title text-center">
            <img src="./book.png" height="40" width="40"></img> ORDERBOOK{" "}
            <img src="./book.png" height="40" width="40"></img>
          </h1>
        </Col>
      </Row>
    </>
  );
}
