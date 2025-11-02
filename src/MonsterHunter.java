import java.util.*;

public class MonsterHunter {

    public static void main(String[] args) {
        int[][] monsters = {{1, 1}, {2, 3}, {4, 2}};
        int result = findMinCoins(monsters);
        System.out.println("Минимальное количество монет: " + result);
    }

    // Основная функция решения
    public static int findMinCoins(int[][] monsters) {
        if (monsters.length == 0) return 0;
        if (monsters.length == 1) return 1; // Один монстр - квадрат 1x1

        int minCost = Integer.MAX_VALUE;

        // Вариант 1: Никто не двигается
        minCost = Math.min(minCost, minRectangleStatic(monsters));

        // Вариант 2: Один монстр делает ход
        for (int i = 0; i < monsters.length; i++) {
            int x = monsters[i][0];
            int y = monsters[i][1];

            // Все возможные ходы
            int[][] moves = {
                    {x + 1, y}, {x - 1, y},
                    {x, y + 1}, {x, y - 1}
            };

            for (int[] move : moves) {
                int newX = move[0];
                int newY = move[1];

                // Проверяем, свободна ли клетка
                if (isCellFree(newX, newY, monsters, i)) {
                    // Создаем новую конфигурацию
                    int[][] newConfig = createNewConfiguration(monsters, i, newX, newY);

                    // Вычисляем минимальный прямоугольник
                    int cost = minRectangleStatic(newConfig);
                    minCost = Math.min(minCost, cost);
                }
            }
        }

        return minCost;
    }

    // Создание новой конфигурации после хода
    private static int[][] createNewConfiguration(int[][] monsters, int movedIndex, int newX, int newY) {
        int[][] newConfig = new int[monsters.length][2];
        for (int i = 0; i < monsters.length; i++) {
            if (i == movedIndex) {
                newConfig[i] = new int[]{newX, newY};
            } else {
                newConfig[i] = monsters[i].clone();
            }
        }
        return newConfig;
    }

    // Проверка свободности клетки
    private static boolean isCellFree(int x, int y, int[][] monsters, int excludeIndex) {
        for (int i = 0; i < monsters.length; i++) {
            if (i != excludeIndex && monsters[i][0] == x && monsters[i][1] == y) {
                return false;
            }
        }
        return true;
    }

    // Вычисление минимального bounding box
    private static int minRectangleStatic(int[][] monsters) {
        if (monsters.length == 0) return 0;

        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;

        for (int[] monster : monsters) {
            minX = Math.min(minX, monster[0]);
            maxX = Math.max(maxX, monster[0]);
            minY = Math.min(minY, monster[1]);
            maxY = Math.max(maxY, monster[1]);
        }

        return (maxX - minX + 1) * (maxY - minY + 1);
    }
}