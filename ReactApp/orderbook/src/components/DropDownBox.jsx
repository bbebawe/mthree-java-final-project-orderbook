import React from "react";
import Autosuggest from "react-autosuggest";
import "./style/dropdown.css";

export default class DropDownBox extends React.Component {
  constructor() {
    super();
    this.state = {
      value: "",
      suggestions: [],
    };
    this.onSuggestionsFetchRequested = this.onSuggestionsFetchRequested.bind(
      this
    );
    this.onSuggestionsClearRequested = this.onSuggestionsClearRequested.bind(
      this
    );
    this.onChange = this.onChange.bind(this);
  }

  getSuggestions = (value) => {
    const inputValue = value.trim().toLowerCase();
    const inputLength = inputValue.length;

    return inputLength === 0
      ? []
      : this.props.data.filter(
        (lang) => lang.name.toLowerCase().slice(0, inputLength) === inputValue
      );
  };

  getSuggestionValue = (suggestion) => {
    return suggestion.name;
  };

  renderSuggestion = (suggestion) => (
    <div>
      {suggestion.symbol} : {suggestion.name}
    </div>
  );

  onChange = (event, { newValue }) => {
    this.setState(
      {
        value: newValue,
      },
      function () {
        const s = this.state.value;
        this.props.handleSelection(this.props.data.filter((x) => x.name === s));
      }.bind(this)
    );
  };

  onSuggestionsFetchRequested = ({ value }) => {
    this.setState({
      suggestions: this.getSuggestions(value),
    });
  };

  onSuggestionsClearRequested = () => {
    this.setState({
      suggestions: [],
    });
  };

  render() {
    const { value, suggestions } = this.state;

    const inputProps = {
      placeholder: "Select " + this.props.title + ":",
      value,
      onChange: this.onChange,
    };
    return (
      <Autosuggest
        suggestions={suggestions}
        onSuggestionsFetchRequested={this.onSuggestionsFetchRequested}
        onSuggestionsClearRequested={this.onSuggestionsClearRequested}
        getSuggestionValue={this.getSuggestionValue}
        renderSuggestion={this.renderSuggestion}
        inputProps={inputProps}
      />
    );
  }
}
