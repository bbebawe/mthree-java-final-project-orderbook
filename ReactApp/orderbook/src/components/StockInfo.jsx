import React from "react";
import { Col, Form, Row } from "react-bootstrap";
import Button from "./Button";
import DropDownBox from "./DropDownBox";
import "./style/StockInfo.css";

export default class StockInfo extends React.Component {
  constructor() {
    super();
    this.state = {
      selection: "none",
      stockDisplay: "- - - -",
      showError: false,
      message: "",
      existError: "Stock not listed on exchange.",
      selectError: "Please select a stock.",
      displayButton: false,
    };
    this.setSelection = this.setSelection.bind(this);
    this.getStock = this.getStock.bind(this);
    this.DropDownBox1 = React.createRef();
  }

  setSelection = (sel) => {
    this.setState({
      selection: sel[0],
    });
  };

  displayCurrentStock = () => {
    let selected = this.state.selection;
    this.setState({
      stockDisplay: selected["symbol"],
      displayButton: true,
    });
  };

  showMessage() {
    return <h6 id="error-message">{this.state.message}</h6>;
  }

  showButton() {
    //console.log(this.props.buttonDisabled)
    return (
      <Button
        id="historical-trades-btn"
        name={this.props.name}
        disable={this.props.buttonDisabled}
        clickHandler={this.props.historicalTradesHandler}
      />
    );
  }

  clearInput = () => {
    this.DropDownBox1.current.state.value = "";
    this.setState({
      selection: "none",
      showError: false,
    });
  };

  handleNoSuchStock(note) {
    this.setState({
      message: note,
      showError: true,
    });
  }

  async getStock() {
    let selected = this.state.selection;
    if (selected === undefined) {
      this.handleNoSuchStock(this.state.existError);
    } else if (selected === "none") {
      this.handleNoSuchStock(this.state.selectError);
    } else {
      await this.props.setSelectedStock(selected);
      this.props.stockHandler();
      this.displayCurrentStock();
      this.clearInput();
    }
  }

  render() {
    //console.log(this.props.buttonDisabled)
    return (
      <>
        <Form>
          <DropDownBox
            ref={this.DropDownBox1}
            data={this.props.stocks}
            title="Stock"
            selection={this.state.selection}
            handleSelection={this.setSelection}
          />
          <Row>
            <Col sm={6}>{this.state.showError ? this.showMessage() : null}</Col>
            <Col id="button-col" sm={6}>
              <Button
                className="button"
                id="get-btn"
                name="GET ORDERS"
                disable={false}
                clickHandler={this.getStock}
              />
            </Col>
          </Row>
          <Form.Group>
            <div className="currentStock">
              <span className="stock-span left-text">Current Stock</span>
              <span className="selected-span">{this.state.stockDisplay}</span>
            </div>
            {this.showButton()}
          </Form.Group>
        </Form>
      </>
    );
  }
}
