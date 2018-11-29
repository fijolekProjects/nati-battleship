import React, {Component} from 'react';
import * as HttpService from "./HttpService";
import './App.css';

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
        this.setState({hittedPointsPlayer1: data[0].hittedShipsPlayer});
        this.setState({mishitsPlayer1: data[0].mishitsPlayer});
        this.setState({turnPlayer1: data[0].yourTurn});
        this.setState({player2Id: data[1].playerId});
        this.setState({boardPlayer2: data[1].board.board});
        this.setState({hittedPointsPlayer2: data[1].hittedShipsPlayer});
        this.setState({mishitsPlayer2: data[1].mishitsPlayer});
        this.setState({turnPlayer2: data[1].yourTurn});
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
              return <div className={"hittedShip"} key={idx + i}></div>
            } else if (hittedPoints && this.drawHittedPoints(column.rowId, column.columnId, mishits)) {
              return <div className={"mishit"} key={idx + i}></div>
            } else {
              return <div className={"column"} key={idx + i} onClick={(event) => {
                this.handleOnClick(column.rowId, column.columnId, playerId)
              }}></div>
            }
          }
        )}
      </div>
    }) : null);
  };

  render() {
    const classPlayer1 = this.state.turnPlayer1 ? "player1" : "hiddenPlayer1";
    const classPlayer2 = this.state.turnPlayer2 ? "player2" : "hiddenPlayer2";
    return (
      <div className="App">
        <h1>Play NatiBattleship</h1>
        <div
          className={classPlayer1}>
          <h3>Player 1</h3>
          {this.generatePlayerBoard(this.state.boardPlayer1, this.state.hittedPointsPlayer1, this.state.mishitsPlayer1, this.state.player1Id)}
        </div>
        <div
          className={classPlayer2}>
          <h3>Player 2</h3>
          {this.generatePlayerBoard(this.state.boardPlayer2, this.state.hittedPointsPlayer2, this.state.mishitsPlayer2, this.state.player2Id)}
        </div>
      </div>
    );
  }
}

export default App;
