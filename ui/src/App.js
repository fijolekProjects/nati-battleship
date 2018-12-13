import React, {Component} from 'react';
import * as HttpService from "./HttpService";
import './App.css';
import _ from 'lodash';
import {library} from '@fortawesome/fontawesome-svg-core'
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faShip, faTimes} from '@fortawesome/free-solid-svg-icons'

library.add(faTimes);
library.add(faShip);


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
      statusGame: null,
    }
  }

  componentDidMount() {
    this.newGame();
  };

  newGame = () => {
    HttpService.fetchJson('newGame')
      .then(data => {
        console.log(data)
        this.setGameStatus(data);
      })
  }

  getGameStatus = (body) => {
    this.play(body);
  };

  play = (body) => {
    HttpService.postJson('player', body)
      .then(data => {
        return data.json();
      }).then(data => {
      this.setGameStatus(data);
      if (this.state.statusGame === false) {
        this.whoWon(this.state.turnPlayer1)
      }
      if (this.state.turnPlayer1 === false) {
        setTimeout(this.computer, 1000);
      }
    })
  };

  computer = () => {
    HttpService.postJson('computer')
      .then(data => {
        return data.json();
      }).then(data => {
      this.setGameStatus(data);
      if (this.state.statusGame === false) {
        this.whoWon(this.state.turnPlayer1)
      }
      if (this.state.turnPlayer1 === false) {
        setTimeout(this.computer, 1000);
      }
    })
  }

  setGameStatus = (data) => {
    this.setState({
      statusGame: data.gameRunning,
      player1Id: data.player.playerId,
      boardPlayer1: data.player.board.board,
      hittedPointsPlayer1: data.player.hittedShipPoints,
      mishitsPlayer1: data.player.mishitPoints,
      turnPlayer1: data.player.yourTurn,
      hittedShipsPlayer1: data.player.hittedShips,
      player2Id: data.computer.playerId,
      boardPlayer2: data.computer.board.board,
      hittedPointsPlayer2: data.computer.hittedShipPoints,
      mishitsPlayer2: data.computer.mishitPoints,
      turnPlayer2: data.computer.yourTurn,
      hittedShipsPlayer2: data.computer.hittedShips,
    });
  };

  drawHittedPoints = (rowId, columnId, hittedPoints) => {
    return hittedPoints ? hittedPoints.some(point =>
      point.rowId === rowId && point.columnId === columnId) : null;
  };

  handleOnClick = (rowId, columnId) => {
    const hittedBoardPoint = {rowId: rowId, columnId: columnId};
    this.getGameStatus(hittedBoardPoint);
  };

  generatePlayerBoard = (board, hittedPoints, mishits, playerId) => {
    return (board ? board.map((row, idx) => {
      return <div className={"row"} key={idx}>
        {row.map((column, i) => {
            if (hittedPoints && this.drawHittedPoints(column.rowId, column.columnId, hittedPoints)) {
              return <div className={"hittedShip"} key={`${idx}_${i}`}><FontAwesomeIcon icon="ship" color={"red"}/></div>
            } else if (hittedPoints && this.drawHittedPoints(column.rowId, column.columnId, mishits)) {
              return <div className={"mishit"} key={`${idx}_${i}`}><FontAwesomeIcon icon="times" color={"#333333"}/></div>
            } else {
              return <div className={"column"} key={`${idx}_${i}`} onClick={(event) => {
                this.handleOnClick(column.rowId, column.columnId)
              }}>{column.columnId}</div>
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

  whoWon = (playerStatus) => {
    //eslint-disable-next-line
    const info = playerStatus ? confirm("KONIEC GRY! WYGRAŁ GRACZ: " + this.state.player1Id) : confirm("KONIEC GRY! WYGRAŁ GRACZ: " + this.state.player2Id)
    if (info === true) {
      this.setState({statusGame: true})
      this.newGame();
    }
  }

  render() {
    const classPlayer1 = this.state.turnPlayer1 ? "player1" : "hiddenPlayer1";
    const classPlayer2 = this.state.turnPlayer2 ? "player2" : "hiddenPlayer2";
    return (
      <div className="App">
        <div className={"myApp"}>
          <h2><FontAwesomeIcon icon="ship"/>Play NatiBattleship <FontAwesomeIcon icon="ship"/></h2>
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
