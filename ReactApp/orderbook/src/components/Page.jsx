import React from "react";
import { Image, Row, Col, Button } from "react-bootstrap";
import Login from "./Login";
import Table from "./Table";
import StockInfo from "./StockInfo";
import Today from "./Today";
import MakeTrade from "./MakeTrade";
import "./style/page.css";
import UpdateOrder from "./UpdateOrderModal";

// const SERVICE_URL = "http://localhost:8080/";
const SERVICE_URL =
  "http://ec2-3-15-16-231.us-east-2.compute.amazonaws.com:8080/";
export default class Page extends React.Component {
  constructor() {
    super();
    this.state = {
      loggedIn: false,
      loggedUser: {
        username: "",
        password: "",
      },
      loginError: "",
      loginSpinner: false,
      dataSpinner: false,
      show: false,
      graphDataPrice: [],
      graphDataTime: [],
      historicalTrades: {
        id: 2,
        name: "SHOW HISTORICAL TRADES",
        displayValue: true,
      },
      columnHeaders: {
        buyTableColumnNames: {
          column1: "Order ID",
          column2: "Time",
          column3: "MPID",
          column4: "Shares",
          column5: "Bid",
        },
        historicalTableColumnNames: {
          column1: "Time",
          column2: "Shares",
          column3: "Buyer MPID",
          column4: "Seller MPID",
          column5: "Price",
        },
        sellTableColumnNames: {
          column1: "Ask",
          column2: "Shares",
          column3: "MPID",
          column4: "Time",
          column5: "Order ID",
        },
      },
      loading: false,
      buyTable: [],
      sellTable: [],
      historicalTradesData: [],
      selectedStock: {
        id: "",
        name: "",
        symbol: "",
      },
      todaysActivity: {
        orderVolume: "N/A",
        tradeVolume: "N/A",
        lastMatch: "N/A",
        time: "N/A",
        spread: "N/A",
      },
      stocks: [],
      participants: [],
      showupdate: false,
      newOrder: {
        price: "",
        quantity: "",
        side: "",
        marketParticipant: "",
        stock: "",
      },
      showModal: false,
      selectedOrder: {
        id: "",
        side: "",
        marketParticipant: "",
        quantity: "",
        price: "",
        time: "",
        stock: "",
      },
      buttonDisabled: true,
    };
    this.showOrders = this.showOrders.bind(this);
    this.setSelectedStock = this.setSelectedStock.bind(this);
    this.showHistoricalTrades = this.showHistoricalTrades.bind(this);
    this.getParticipants = this.getParticipants.bind(this);
    this.getStocks = this.getStocks.bind(this);
    this.getTodaysActivity = this.getTodaysActivity.bind(this);
    this.getStocks = this.getStocks.bind(this);
    this.sendLoginRequest = this.sendLoginRequest.bind(this);
    this.handleUsernameChange = this.handleUsernameChange.bind(this);
    this.handlePasswordChange = this.handlePasswordChange.bind(this);
    this.handleLogout = this.handleLogout.bind(this);
    this.handleKeyDown = this.handleKeyDown.bind(this);
  }

  handleUsernameChange(e) {
    let user = {
      username: e.target.value,
      password: this.state.loggedUser.password,
    };
    this.setState({
      loggedUser: user,
    });
  }

  handlePasswordChange(e) {
    let user = {
      username: this.state.loggedUser.username,
      password: e.target.value,
    };
    this.setState({
      loggedUser: user,
    });
  }

  handleKeyDown(e) {
    if (e.key === "Enter") {
      this.sendLoginRequest();
    }
  }

  sendLoginRequest() {
    this.setState({
      loginError: "",
    });
    if (
      !/\s{1}/.test(this.state.loggedUser.password) &&
      !/\s{1}/.test(this.state.loggedUser.username) &&
      this.state.loggedUser.password.length !== 0 &&
      this.state.loggedUser.username.length !== 0
    ) {
      this.setState({
        loginSpinner: true,
      });
      fetch(SERVICE_URL + "login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.state.loggedUser),
      })
        .then((response) => {
          if (response.status === 200) {
            return response.json();
          }
        })
        .then((data) => {
          this.setState({
            loginSpinner: false,
          });
          if (data !== undefined) {
            this.setState({
              loggedIn: true,
              loggedUser: data,
            });
          } else {
            this.setState({
              loginError: "Invalid username or password",
            });
          }
        });
    } else {
      this.setState({
        loginError: "Fields can not be blank or contain spaces",
      });
    }
  }

  handleLogout() {
    this.setState({
      loggedIn: false,
      show: false,
      loggedUser: {
        username: "",
        password: "",
      },
    });
  }

  async showOrders() {
    this.setState({
      historicalTrades: {
        name: "SHOW HISTORICAL TRADES",
        displayValue: true,
        buttonDisabled: true,
      },
    });

    if (this.state.selectedStock.id !== "") {
      this.setState({
        show: true,
      });
    }
    this.setState({
      buyTable: [],
      loading: true,
    });
    await fetch(
      SERVICE_URL + "orders/bids/" + this.state.selectedStock.id
    ).then((response) => {
      if (response.status === 200) {
        response
          .json()
          .then((data) => this.setState({ buyTable: data, loading: false }));
      }
    });
    this.setState({
      sellTable: [],
      loading: true,
    });

    await fetch(
      SERVICE_URL + "orders/offers/" + this.state.selectedStock.id
    ).then((response) => {
      if (response.status === 200) {
        response.json().then((data) => {
          this.setState({ sellTable: data, loading: false });
        });
      }
    });

    this.getTodaysActivity();
    this.setState({ buttonDisabled: false });
    await this.loadHistoricaData();
  }

  setSelectedStock = (stock) => {
    this.setState({
      selectedStock: stock,
    });
  };

  loadHistoricaData() {
    fetch(SERVICE_URL + "trades/" + this.state.selectedStock.id).then(
      (response) => {
        if (response.status == 200) {
          response.json().then((data) => {
            this.setState({
              historicalTradesData: data,
              graphDataPrice: data.map((a) => a.price).reverse(),
              graphDataTime: data
                .map((a) => a.tradeTime.slice(0, 16))
                .reverse(),
              loading: false,
              buttonDisabled: false,
            });
          });
        } else {
          this.setState({ buttonDisabled: true });
        }
      }
    );
  }

  setNewOrder = (orderInfo) => {
    let order = this.state.newOrder;
    order.price = orderInfo.price;
    order.quantity = orderInfo.quantity;
    order.side = orderInfo.side;
    order.marketParticipant = orderInfo.mpSelection;
    order.stock = this.state.selectedStock;
    this.setState({
      newOrder: order,
    });
  };

  selectOrderToUpdate = (orderInfo) => {
    let order = this.state.selectedOrder;
    order.price = orderInfo.price;
    order.quantity = orderInfo.quantity;
    order.side = orderInfo.side;
    order.marketParticipant = orderInfo.marketParticipant;
    order.id = orderInfo.id;
    order.time = orderInfo.time;
    this.setState({
      showModal: true,
      selectedOrder: order,
    });
  };

  showHistoricalTrades = () => {
    let status = !this.state.historicalTrades.displayValue;
    let name = "SHOW ACTIVE ORDERS";
    this.setState({ buttonDisabled: false, dataSpinner: true });
    if (status) {
      name = "SHOW HISTORICAL TRADES";
      this.setState({ loading: true });
      fetch(SERVICE_URL + "orders/bids/" + this.state.selectedStock.id).then(
        (response) => {
          if (response.status === 200) {
            response
              .json()
              .then((data) =>
                this.setState({ buyTable: data, loading: false })
              );
          }
        }
      );

      this.setState({ loading: true });
      fetch(SERVICE_URL + "orders/offers/" + this.state.selectedStock.id).then(
        (response) => {
          if (response.status === 200) {
            response
              .json()
              .then((data) =>
                this.setState({ sellTable: data, loading: false })
              );
          }
        }
      );
    } else {
      this.setState({
        loading: true,
        historicalTradesData: [],
        graphDataPrice: [],
        graphDataTime: [],
      });
      this.loadHistoricaData();
    }
    this.setState({
      historicalTrades: {
        name: name,
        displayValue: status,
      },
      dataSpinner: false,
    });
  };

  getParticipants() {
    fetch(SERVICE_URL + "participants/", {
      method: "GET",
      headers: {
        accept: "application/json",
      },
    }).then((response) => {
      if (response.status === 200) {
        response.json().then((data) => {
          this.setState({
            participants: data,
          });
        });
      }
    });
  }

  getStocks() {
    let responseCode = "";
    fetch(SERVICE_URL + "stocks/", {
      method: "GET",
      headers: {
        accept: "application/json",
      },
    })
      .then((response) => {
        responseCode = response.status;
        return response.json();
      })
      .then((data) => {
        if (responseCode === 200) {
          this.setState({
            stocks: data,
          });
        }
      });
  }

  placeOrder = () => {
    fetch(SERVICE_URL + "orders/add", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(this.state.newOrder),
    }).then((response) => {
      this.showOrders();
    });
  };

  getTodaysActivity() {
    let activities = this.state.todaysActivity;
    for (let x in activities) {
      activities[x] = "Loading...";
    }
    this.setState({
      todaysActivity: activities,
    });
    fetch(SERVICE_URL + "orders/volume/" + this.state.selectedStock.id).then(
      (response) => {
        if (response.status === 200) {
          response.json().then((data) => {
            let activities = this.state.todaysActivity;
            activities.orderVolume = data;
            this.setState({
              todaysActivity: activities,
            });
          });
        }
      }
    );

    fetch(SERVICE_URL + "trades/volume/" + this.state.selectedStock.id).then(
      (response) => {
        if (response.status === 200) {
          response.json().then((data) => {
            let activities = this.state.todaysActivity;
            activities.tradeVolume = data;
            if (this.state.buyTable[0] && this.state.sellTable[0]) {
              activities.spread =
                "£" +
                (
                  this.state.sellTable[0].price - this.state.buyTable[0].price
                ).toFixed(2);
            } else {
              activities.spread = "N/A";
            }
            this.setState({
              todaysActivity: activities,
            });
          });
        }
      }
    );

    fetch(SERVICE_URL + "trades/latest/" + this.state.selectedStock.id).then(
      (response) => {
        let activities = this.state.todaysActivity;
        if (response.status === 200) {
          response.json().then((data) => {
            activities.lastMatch = "£" + data.price.toFixed(2);
            activities.time =
              data.tradeTime.slice(0, 10) + " " + data.tradeTime.slice(11, 19);
            this.setState({
              todaysActivity: activities,
            });
          });
        } else {
          activities.lastMatch = "N/A";
          activities.time = "N/A";
          this.setState({
            todaysActivity: activities,
          });
        }
      }
    );
  }

  // note this delete method works but page needs to re render for table update
  deleteById = (orderId) => {
    console.log(orderId);
    fetch(SERVICE_URL + "orders/" + orderId, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    }).then((response) => {
      this.showOrders();
    });
    
  }

  updateOrder = (updatedOrder) => {
    let orderObj = {
      price: updatedOrder.price,
      quantity: updatedOrder.quantity,
      side: updatedOrder.side,
      marketParticipant: updatedOrder.marketParticipant,
      stock: this.state.selectedStock,
    };
    let sel = updatedOrder.id;
    fetch(SERVICE_URL + "orders/add", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(orderObj),
    }).then((response) => {
      this.showOrders();
    });
  }

  onCloseModal = () => {
    this.setState({
      showModal: false,
    });
  };

  componentDidMount() {
    this.getStocks();
    this.getParticipants();
  }

  displayModal() {
    return (
      <UpdateOrder
        id="update-modal"
        onClose={this.onCloseModal}
        shown={this.state.showModal}
        order={this.state.selectedOrder}
        deleteOrder={this.deleteById}
        updateOrder={this.updateOrder}
      />
    );
  }

  render() {
    console.log(this.state.newOrder);
    return (
      <>
        {!this.state.loggedIn && (
          <Row>
            <Login
              loginSpinner={this.state.loginSpinner}
              loggedUser={this.state.loggedUser}
              loginError={this.state.loginError}
              handleUsernameChange={this.handleUsernameChange}
              handlePasswordChange={this.handlePasswordChange}
              loginHandler={this.sendLoginRequest}
              handleKeyDown={this.handleKeyDown}
            />
          </Row>
        )}
        {this.state.loggedIn && (
          <Row className="Row1 pl-5 pr-5">
            <Col md={12} className="login-info text-right mb-3">
              <span>Logged As: {this.state.loggedUser.username} </span>
              <Button id="logout-btn" onClick={this.handleLogout}>
                Logout
              </Button>
            </Col>
            <Col xs={12} md={4} className="stock-info-col text-right">
              <StockInfo
                name={this.state.historicalTrades.name}
                stockHandler={this.showOrders}
                historicalTradesHandler={this.showHistoricalTrades}
                stocks={this.state.stocks}
                setSelectedStock={this.setSelectedStock}
                buttonDisabled={this.state.buttonDisabled}
              />
            </Col>
            <Col xs={12} md={4} className="activity-col text-right">
              {this.state.showModal ? this.displayModal() : null}
              {this.state.show && <Today info={this.state.todaysActivity} />}
            </Col>
            <Col xs={12} md={4} className="trade-col text-center p-4">
              {this.state.show && (
                <MakeTrade
                  participants={this.state.participants}
                  setOrder={this.setNewOrder}
                  placeOrder={this.placeOrder}
                />
              )}
            </Col>
          </Row>
        )}
        {this.state.show && (
          <Row className="pl-5 pr-5 pt-3 trades-row">
            {this.state.dataSpinner && (
              <Col md={12}>
                <Image
                  src="./spinner.gif"
                  className=" mx-auto d-block login-spinner"
                ></Image>
              </Col>
            )}
            <Col
              xs={12}
              md={6}
              id="buy-table"
              className="Column4 text-center pb-4 pr-0"
            >
              <Table
                type="buy"
                tableInfo={this.state.buyTable}
                columnInfo={this.state.columnHeaders}
                historicalInfo={this.state.historicalTradesData}
                value={this.state.historicalTrades.displayValue}
                selectOrder={this.selectOrderToUpdate}
              />
            </Col>
            <Col
              xs={12}
              md={6}
              id="sell-table"
              className="Column5 text-center pl-0"
            >
              <Table
                type="sell"
                tableInfo={this.state.sellTable}
                columnInfo={this.state.columnHeaders}
                selectOrder={this.selectOrderToUpdate}
                historicalInfo={this.state.historicalTradesData}
                value={this.state.historicalTrades.displayValue}
                graphDataPrice={this.state.graphDataPrice}
                graphDataTime={this.state.graphDataTime}
              />
            </Col>
          </Row>
        )}
      </>
    );
  }
}
