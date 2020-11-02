import React, { Component } from "react";
import { Form, Button, Image } from "react-bootstrap";
export default class Login extends Component {
  render() {
    return (
      <>
        <Form
          className="login-form"
          onSubmit={(e) => e.preventDefault()}
          onKeyDown={this.props.handleKeyDown}
        >
          <span className="login-error">{this.props.loginError}</span>
          <Form.Group>
            <Form.Label htmlFor="username">Username</Form.Label>
            <Form.Control
              type="text"
              id="username"
              value={this.props.loggedUser.username}
              onChange={this.props.handleUsernameChange}
            />
          </Form.Group>
          <Form.Group>
            <Form.Label htmlFor="password">Password</Form.Label>
            <Form.Control
              type="password"
              id="password"
              value={this.props.loggedUser.username.password}
              onChange={this.props.handlePasswordChange}
            />
          </Form.Group>
          {this.props.loginSpinner && (
            <Image
              src="./spinner.gif"
              className=" mx-auto d-block login-spinner"
            ></Image>
          )}
          <Button onClick={this.props.loginHandler}>Login</Button>
        </Form>
      </>
    );
  }
}
