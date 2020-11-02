import React from "react";
import Graph from "./Graph";
import "./style/page.css";

export default class Table extends React.Component {
  constructor() {
    super();
    this.state = {
      price: "",
      quantity: "",
      side: "",
      marketParticipant: "",
      id: "",
      time: "",
    };
  }

  async grabOrder(tag) {
    let selected = this.props.tableInfo.filter((x) => x.id === tag);
    selected = selected[0];
    await this.setState({
      id: selected.id,
      price: selected.price,
      quantity: selected.quantity,
      time: selected.orderTime,
      marketParticipant: selected.marketParticipant,
      side: selected.side,
    });
    this.props.selectOrder(this.state);
  }

  renderElement() {
    let header = this.props.columnInfo;
    let type = this.props.type;
    let active = this.props.value;
    if (type === "sell" && active) {
      header = header.sellTableColumnNames;
    } else if (type === "buy" && active) {
      header = header.buyTableColumnNames;
    } else if (type === "buy") {
      header = header.historicalTableColumnNames;
    } else {
      return (
        <Graph
          className="graph"
          dataPrice={this.props.graphDataPrice}
          dataTime={this.props.graphDataTime}
        />
      );
    }
    return this.renderHeader(header, active, type);
  }

  renderHeader(header, active, type) {
    let head = "";
    if (active && type === "sell") {
      head = "SELL ORDERS";
    } else if (active && type === "buy") {
      head = "BUY ORDERS";
    } else {
      head = "HISTORICAL TRADES";
    }
    return (
      <>
        <h4 className="tableTitle pt-3">{head}</h4>
        <hr className="Line2 border-bottom-0"></hr>
        <div className="table-wrapper-scroll-y my-custom-scrollbar">
          <table className="DefaultTable">
            <thead>
              <tr>
                <th>{header.column1}</th>
                <th>{header.column2}</th>
                <th>{header.column3}</th>
                <th>{header.column4}</th>
                <th>{header.column5}</th>
              </tr>
            </thead>
            {this.renderInfo(active, type)}
          </table>
        </div>
      </>
    );
  }

  renderRows(info, active, type) {
    if (type === "sell" && active) {
      return (
        <>
          <td className ="sellPrice">{parseFloat(info.price).toFixed(2)}</td>
          <td>{info.quantity}</td>
          <td>
            <a
              href=" "
              onClick={(e) => e.preventDefault()}
              title={
                info["marketParticipant"]["name"] +
                ", " +
                info["marketParticipant"]["description"]
              }
              style={{ color: "black" }}
            >
              {info["marketParticipant"]["mpid"]}
            </a>
          </td>
          <td>
            {info.orderTime.slice(0, 10) + " " + info.orderTime.slice(11, 19)}
          </td>
          <td>
            <div id="grab" onClick={() => this.grabOrder(info.id)}>
              {info.id}
            </div>
          </td>
        </>
      );
    } else if (type === "buy" && active) {
      return (
        <>
          <td>
            <div id="grab" onClick={() => this.grabOrder(info.id)}>
              {info.id}
            </div>
          </td>
          <td>
            {info.orderTime.slice(0, 10) + " " + info.orderTime.slice(11, 19)}
          </td>
          <td>
            <a
              href=""
              onClick={(e) => e.preventDefault()}
              title={
                info["marketParticipant"]["name"] +
                ", " +
                info["marketParticipant"]["description"]
              }
              style={{ color: "black" }}
            >
              {info["marketParticipant"]["mpid"]}
            </a>
          </td>
          <td>{info.quantity}</td>
          <td className ="buyPrice">{parseFloat(info.price).toFixed(2)}</td>
        </>
      );
    } else {
      return (
        <>
          <td>
            {info.tradeTime.slice(0, 10) + " " + info.tradeTime.slice(11, 19)}
          </td>
          <td>{info.quantity}</td>
          <td>
            <a
              href=""
              onClick={(e) => e.preventDefault()}
              title={
                info.orders[0]["marketParticipant"]["name"] +
                ", " +
                info.orders[0]["marketParticipant"]["description"]
              }
              style={{ color: "black" }}
            >
              {info.orders[0]["marketParticipant"]["mpid"]}
            </a>
          </td>
          <td>
            <a
              href=""
              onClick={(e) => e.preventDefault()}
              title={
                info.orders[1]["marketParticipant"]["name"] +
                ", " +
                info.orders[1]["marketParticipant"]["description"]
              }
              style={{ color: "black" }}
            >
              {info.orders[1]["marketParticipant"]["mpid"]}
            </a>
          </td>
          <td>{parseFloat(info.price).toFixed(2)}</td>
        </>
      );
    }
  }

  renderInfo(active, type) {
    let data = this.props;
    if (active) {
      data = data.tableInfo;
    } else {
      data = data.historicalInfo;
    }
    return (
      <tbody>
        {data.map((info, i) => {
          return <tr key={i}>{this.renderRows(info, active, type)}</tr>;
        })}
      </tbody>
    );
  }

  render() {
    return <div>{this.renderElement()}</div>;
  }
}
