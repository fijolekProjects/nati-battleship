import React, {Component} from 'react';
import * as HttpService from "./HttpService";
import './App.css';
import _ from 'lodash';

class App extends Component {
  constructor() {
    super();
    this.state = {
      player1Id: null,
      player2Id: null,
      boardPlayer1: [],
      boardPlayer2: [],
      hittedPointsPlayer1: null,
      hittedPointsPlayer2: null,
      mishitsPlayer1: null,
      mishitsPlayer2: null,
      hittedShipsPlayer1: null,
      hittedShipsPlayer2: null,
      turnPlayer1: null,
      turnPlayer2: null,
    }
  }

  componentDidMount() {
    this.getGameStatus();
  };

  getGameStatus = (body) => {
    if (typeof body === "undefined") {
      body = null;
    }
    HttpService.postJson('players', body)
      .then(data => data.json())
      .then(data => {
        this.setState({player1Id: data[0].playerId});
        this.setState({boardPlayer1: data[0].board.board});
        this.setState({hittedPointsPlayer1: data[0].hittedShipPoints});
        this.setState({mishitsPlayer1: data[0].mishitPoints});
        this.setState({turnPlayer1: data[0].yourTurn});
        this.setState({hittedShipsPlayer1: data[0].hittedShips});
        this.setState({player2Id: data[1].playerId});
        this.setState({boardPlayer2: data[1].board.board});
        this.setState({hittedPointsPlayer2: data[1].hittedShipPoints});
        this.setState({mishitsPlayer2: data[1].mishitPoints});
        this.setState({turnPlayer2: data[1].yourTurn});
        this.setState({hittedShipsPlayer2: data[1].hittedShips});
        console.log("data", data)
      })
  };

  drawHittedPoints = (rowId, columnId, hittedPoints) => {
    return hittedPoints ? hittedPoints.some(point =>
      point.rowId === rowId && point.columnId === columnId) : null;
  };

  handleOnClick = (rowId, columnId, playerId) => {
    const hittedBoardPoint = {playerId: playerId, boardPoint: {rowId: rowId, columnId: columnId}};
    this.getGameStatus(hittedBoardPoint);
  };

  generatePlayerBoard = (board, hittedPoints, mishits, playerId) => {
    return (board ? board.map((row, idx) => {
      return <div className={"row"} key={idx}>
        {row.map((column, i) => {
            if (hittedPoints && this.drawHittedPoints(column.rowId, column.columnId, hittedPoints)) {
              return <div className={"hittedShip"} key={`${idx}_${i}`}></div>
            } else if (hittedPoints && this.drawHittedPoints(column.rowId, column.columnId, mishits)) {
              return <div className={"mishit"} key={`${idx}_${i}`}></div>
            } else {
              return <div className={"column"} key={`${idx}_${i}`} onClick={(event) => {
                this.handleOnClick(column.rowId, column.columnId, playerId)
              }}></div>
            }
          }
        )}
      </div>
    }) : null);
  };

  generateHittedShips = (hittedShips, k) => {
    const hittedShipsGrouped = _.groupBy(hittedShips, (i => i.length));
    return hittedShipsGrouped[k] ? hittedShipsGrouped[k].length : 0;
  };

  drawShips = (hittedShips, k, l) => {
    const hitted = this.generateHittedShips(hittedShips, k);
    if (hitted !== l) {
      return <div className={"drawShips"}>
        <div className={`decker${k}`}>{hitted}/{l}</div>
      </div>;
    } else {
      return <div className={"drawShips"}>
        <div className={`hiddenDecker${k}`}>{hitted}/{l}</div>
      </div>;
    }
  };

  render() {
    const classPlayer1 = this.state.turnPlayer1 ? "player1" : "hiddenPlayer1";
    const classPlayer2 = this.state.turnPlayer2 ? "player2" : "hiddenPlayer2";
    return (
      <div className="App">
        <div className={"myApp"}>
          <h1>Play NatiBattleship</h1>
          <div
            className={"board"}>
            <h3>Player 1</h3>
            <div
              className={classPlayer1}>{this.generatePlayerBoard(this.state.boardPlayer1, this.state.hittedPointsPlayer1, this.state.mishitsPlayer1, this.state.player1Id)}</div>
            {this.drawShips(this.state.hittedShipsPlayer1, 1, 4)}
            {this.drawShips(this.state.hittedShipsPlayer1, 2, 3)}
            {this.drawShips(this.state.hittedShipsPlayer1, 3, 2)}
            {this.drawShips(this.state.hittedShipsPlayer1, 4, 1)}
          </div>
          <div
            className={"board"}>
            <h3>Player 2</h3>
            <div
              className={classPlayer2}>{this.generatePlayerBoard(this.state.boardPlayer2, this.state.hittedPointsPlayer2, this.state.mishitsPlayer2, this.state.player2Id)}</div>
            {this.drawShips(this.state.hittedShipsPlayer2, 1, 4)}
            {this.drawShips(this.state.hittedShipsPlayer2, 2, 3)}
            {this.drawShips(this.state.hittedShipsPlayer2, 3, 2)}
            {this.drawShips(this.state.hittedShipsPlayer2, 4, 1)}
          </div>
        </div>
      </div>
    );
  }
}

export default App;
