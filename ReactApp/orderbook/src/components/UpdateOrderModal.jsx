import React from "react";
import { Form, FormControl, Col, Modal, Row } from "react-bootstrap";
import Button from "./Button";
import "./style/UpdateOrder.css";

export default class UpdateOrder extends React.Component {
  constructor() {
    super();
    this.state = {
      show: "",
      type: "",
      date: "",
      time: "",
      side: "",
      datetime: "",
      id: "",
      quantity: "",
      price: "",
      marketParticipant: {
        id: "",
        name: "",
        mpid: "",
        description: "",
      },
    };
    this.handleChange = this.handleChange.bind(this);
  }

  componentDidMount = () => {
    let orderType = "Buy Order";
    if ((this.props.order.side === "SELL")) {
      orderType = "Sell Order";
    }
    let datetime = this.props.order.time;
    let date = datetime.substring(0, 10);
    let time = datetime.substring(11, 19);
    this.setState({
      show: this.props.shown,
      setShow: this.props.shown,
      type: orderType,
      date: date,
      time: time,
      id: this.props.order.id,
      quantity: this.props.order.quantity,
      side: this.props.order.side,
      datetime: this.props.order.time,
      price: this.props.order.price,
      marketParticipant: {
        id: this.props.order.marketParticipant.id,
        name: this.props.order.marketParticipant.name,
        mpid: this.props.order.marketParticipant.mpid,
        description: this.props.order.marketParticipant.description,
      },
    });
  };

  async handleChange(e) {
    let field = e.target.name;
    let value = "";
    if (field === "price") {
      value = parseFloat(e.target.value).toFixed(2);
    } else {
      value = parseFloat(e.target.value).toFixed(0);
    }
    this.setState({ [field]: value });
  }

  handleClose = () => {
    this.setState({
      show: false,
    });
    this.props.onClose();
  };

  handleShow = () => {
    this.setState({
      show: true,
    });
  };

  updateOrder() {
    this.props.updateOrder(this.state);
    this.props.deleteOrder(this.state.id);
    this.handleClose();
  }

  deleteOrder() {
    this.props.deleteOrder(this.state.id);
    this.handleClose();
  }

  render() {
    return (
      <>
        <Modal show={this.state.show} onHide={this.handleClose}>
          <Modal.Header closeButton>
            <Modal.Title id="modal_head">Update Order</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Row>
              <Col id="middle">
                <Form.Group className="mb-3">
                  <Modal.Title class="col-12 modal-title text-center">
                    Order Details
                  </Modal.Title>
                </Form.Group>
                <Row>
                  <Col>
                    <span>
                      Id:
                      <br />
                      Type:
                      <br />
                      Shares:
                      <br />
                      Price:
                      <br />
                      Participant:
                      <br />
                      Date:
                      <br />
                      Time:
                    </span>
                  </Col>
                  <Col id="fetched">
                    <span>
                      {this.state.id}
                      <br />
                      {this.state.type}
                      <br />
                      {this.state.quantity}
                      <br />
                      {this.state.price}
                      <br />
                      {this.state.marketParticipant.mpid}
                      <br />
                      {this.state.date}
                      <br />
                      {this.state.time}
                    </span>
                  </Col>
                </Row>
              </Col>
              <Col>
                <Form.Group className="mb-3">
                  <Modal.Title class="col-12 modal-title text-center">
                    Update
                  </Modal.Title>
                </Form.Group>
                <Form.Group className="mb-3">
                  <Row>
                    <Col sm={3}>
                      <Form.Label>Price:</Form.Label>
                    </Col>
                    <Col sm={9}>
                      <FormControl
                        id="modalform"
                        type="number"
                        min="0"
                        step=".01"
                        name="price"
                        defaultValue={this.state.price}
                        onChange={this.handleChange}
                      />
                    </Col>
                  </Row>
                </Form.Group>
                <Form.Group className="mb-3">
                  <Row>
                    <Col sm={3}>
                      <Form.Label>Shares:</Form.Label>
                    </Col>
                    <Col sm={9}>
                      <FormControl
                        id="modalform"
                        type="number"
                        name="quantity"
                        min="1"
                        defaultValue={this.state.quantity}
                        onChange={this.handleChange}
                      />
                    </Col>
                  </Row>
                </Form.Group>
              </Col>
            </Row>
          </Modal.Body>
          <Modal.Footer>
            <Col id="update_btns">
              <Button
                name="Delete"
                clickHandler={() => this.deleteOrder()}
              />
            </Col>
            <Col id="update_btns">
              <Button name="Update" 
                clickHandler={() => this.updateOrder()}
              />
            </Col>
            <Col id="update_btns">
              <Button name="Cancel" clickHandler={this.handleClose} />
            </Col>
          </Modal.Footer>
        </Modal>
      </>
    );
  }
}
