import React from "react";
import ReactDOM from "react-dom";
import { Col, Form, FormControl, Container, Row } from "react-bootstrap";
import Button from "./Button";
import DropDownBox from "./DropDownBox";
import "./style/MakeTrade.css";

const side = {
  BID: "BUY",
  OFFER: "SELL",
};

export default class MakeTrade extends React.Component {
  constructor() {
    super();

    this.state = {
      mpSelection: "",
      quantity: "",
      price: "",
      side: side.OFFER,
      showError: false,
      showInputError: false,
      message: "",
      inputMessage: "",
      existError: "Pariticpant not a member of exchange.",
      selectError: "Please select a market paricipant.",
      neagtiveError: "Please enter only non-zero positive numbers",
      noInputError: "Please input a Price and Quantity.",
    };
    this.handleChange = this.handleChange.bind(this);
    this.mpidValue = React.createRef();
  }

  handleChange(e) {
    let field = e.target.name;
    this.setState({ [field]: e.target.value });
    this.inputCheck();
  }

  showMessage(inputError) {
    if (inputError) {
      return <h6 id="error-message">{this.state.inputMessage}</h6>;
    } else {
      return <h6 id="error-message">{this.state.message}</h6>;
    }
    
  }

  setSelection = (sel) => {
    this.setState({
      mpSelection: sel[0],
    });
  };

  handleParticipantError(note) {
    this.setState({
      message: note,
      showError: true,
    });
  }

  handleInputError(note) {
    this.setState({
      inputMessage: note,
      showInputError: true,
    })
  }

  clearInput = () => {
    this.mpidValue.current.state.value = "";
    ReactDOM.findDOMNode(this.refs.priceInput).value = "";
    ReactDOM.findDOMNode(this.refs.quantityInput).value = "";
    this.setState({
      showError: false,
      mpSelection: "",
    });
  };

  placeOrder = () => {
    
    let participant = this.state.mpSelection;
    let priceInput = ReactDOM.findDOMNode(this.refs.priceInput).value;
    let quantityInput = ReactDOM.findDOMNode(this.refs.quantityInput).value;

    if (quantityInput === "" || priceInput === "") {
      this.handleInputError(this.state.noInputError);
    } 

    if (participant === undefined) {
      this.handleParticipantError(this.state.existError);
    } else if (participant === "") {
      this.handleParticipantError(this.state.selectError);
    } else if (this.state.showInputError) {
      return;
    } else {
      this.props.setOrder(this.state);
      this.props.placeOrder();
      this.clearInput();
    }
  };

  inputCheck() {
    let priceInput = ReactDOM.findDOMNode(this.refs.priceInput).value;
    let quantityInput = ReactDOM.findDOMNode(this.refs.quantityInput).value;
    if ( priceInput <= 0 || quantityInput <= 0) {
      this.handleInputError(this.state.neagtiveError);
    } else {
      this.setState({
        showInputError: false,
      })
    }
  }

  render() {
    return (
      <>
        <Container>
          <Row>
            <Col id="price-col">
              <FormControl
                ref="priceInput"
                id="price-input"
                type="number"
                step=".01"
                min="0"
                price={this.state.price}
                onChange={this.handleChange}
                placeholder="Price (£)"
                name="price"
              />
              {this.state.showInputError ? this.showMessage(true) : null}
            </Col>
            <Col id="quantity-col">
              <FormControl
                ref="quantityInput"
                type="number"
                min="1"
                placeholder="Quantity"
                id="quantity-input"
                name="quantity"
                onChange={this.handleChange}
              />
              <FormControl
                id="offer-bid-drpdwn"
                as="select"
                name="side"
                onChange={this.handleChange}
              >
                <option value={side.OFFER}>Offer</option>
                <option value={side.BID}>Bid</option>
              </FormControl>
            </Col>
          </Row>
        </Container>
        <DropDownBox
          ref={this.mpidValue}
          id="mpid"
          title="Market Participant"
          data={this.props.participants}
          selection={this.state.mpidselection}
          handleSelection={this.setSelection}
          onChange={this.handleChange}
        />
        <Row>
          <Col sm={6}>{this.state.showError ? this.showMessage(false) : null}</Col>
          <Col id="place-order-col">
            <Button name="PLACE ORDER" clickHandler={this.placeOrder} />
          </Col>
        </Row>
      </>
    );
  }
}
