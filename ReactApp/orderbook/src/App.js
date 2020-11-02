import React from "react";
import "./App.css";

import Title from "./components/Title";
import Page from "./components/Page";

import { Container } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

function App() {
  return (
    <>
      <Container fluid className="Container App">
        <header>
          <Title />
        </header>
        <body className="App-body">
          <Page />
        </body>
      </Container>
    </>
  );
}

export default App;
