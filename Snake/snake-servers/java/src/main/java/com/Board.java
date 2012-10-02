package com;

import java.util.HashMap;
import java.util.Map;

/**
 * User: oleksandr.baglai
 * Date: 10/2/12
 * Time: 12:07 AM
 */
public class Board {
    public final static char APPLE = '@';
    public final static char STONE = 'X';
    public final static char BODY = '0';
    public final static char EMPTY = ' ';
    public final static char HEAD = '#';
    public final static char WALL = '*';

    private String board;
    private LengthToXY xyl;
    private int size;

    public Board(String boardString) {
        board = boardString;
        size = size();
        xyl = new LengthToXY(size);
    }

    public Point getApple() {
        return xyl.getXY(board.indexOf(APPLE));
    }

    public Point getStone() {
        return xyl.getXY(board.indexOf(STONE));
    }

    public String getSnakeDirection() {
        Point head = getHead();
        Map<String, Integer> bodyAt = new HashMap<String, Integer>();

        if (head.y != 0) {
            if (isAt(head.x, head.y - 1, BODY)) {
                bodyAt.put(Direction.DOWN, xyl.getLength(head.x, head.y - 1));
            }
        }
        if (head.y != size - 1) {
            if (isAt(head.x, head.y + 1, BODY)) {
                bodyAt.put(Direction.UP, xyl.getLength(head.x, head.y + 1));
            }
        }
        if (head.x != 0) {
            if (isAt(head.x - 1, head.y, BODY)) {
                bodyAt.put(Direction.LEFT, xyl.getLength(head.x - 1, head.y));
            }
        }
        if (head.x != size - 1) {
            if (isAt(head.x + 1, head.y, BODY)) {
                bodyAt.put(Direction.RIGHT, xyl.getLength(head.x + 1, head.y));
            }
        }

        if (bodyAt.size() == 1) {
            return inverted(bodyAt.entrySet().iterator().next().getKey());
        }
        return "";
    }

    public boolean isAt(int x, int y, char type) {
        return board.charAt(xyl.getLength(x, y)) == type;
    }

    private String inverted(String direction) {
        if (direction.equals(Direction.LEFT)) {
            return Direction.RIGHT;
        } else if (direction.equals(Direction.RIGHT)) {
            return Direction.LEFT;
        } else if (direction.equals(Direction.DOWN)) {
            return Direction.UP;
        } else {
            return Direction.DOWN;
        }
    }

    public int size() {
        return (int) Math.sqrt(board.length());
    }

    public String fix() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i <= size - 1; i++) {
            buffer.append(board.substring(i*size, (i + 1)*size));
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public Point getHead() {
        return xyl.getXY(board.indexOf(HEAD));
    }
}