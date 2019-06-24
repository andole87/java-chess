package chess.domain.pieces;

import chess.domain.ChessTeam;
import chess.domain.Direction;
import chess.domain.Point;

import java.util.List;
import java.util.Objects;

public abstract class AbstractRangeMovePiece implements Piece {
    final ChessTeam team;
    private final List<Direction> directions;
    private PieceInfo info;

    public AbstractRangeMovePiece(ChessTeam team, PieceInfo info, List<Direction> directions) {
        this.team = team;
        this.info = info;
        this.directions = directions;
    }

    @Override
    public Direction move(Point p1, Point p2) {
        Direction vector = p1.direction(p2).vector();
        if (directions.contains(vector)) {
            return vector;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Direction attack(Point p1, Point p2) {
        return move(p1, p2);
    }

    @Override
    public boolean isAlly(Piece piece) {
        if (piece == null) throw new IllegalArgumentException();
        if (piece instanceof AbstractSingleMovePiece) {
            return this.team == ((AbstractSingleMovePiece) piece).team;
        }
        return this.team == ((AbstractRangeMovePiece) piece).team;
    }

    @Override
    public boolean isTurn(ChessTeam team) {
        return this.team == team;
    }

    @Override
    public double score() {
        return info.score();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractRangeMovePiece that = (AbstractRangeMovePiece) o;
        return team == that.team &&
                Objects.equals(directions, that.directions) &&
                info == that.info;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, directions, info);
    }

    @Override
    public String toString() {
        return team.color() + "," + info.toString();
    }
}