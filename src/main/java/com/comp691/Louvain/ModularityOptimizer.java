package com.comp691.Louvain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import com.comp691.Utils.Config;

public class ModularityOptimizer
{
    public static void main(String[] args) throws IOException
    {
        boolean printOutput, update;
        Clustering clustering;
        double modularity, maxModularity, resolution, resolution2;
        int algorithm, i, j, modularityFunction, nIterations, nRandomStarts;
        long beginTime, endTime, randomSeed;
        Network network;
        Random random;
        String inputFileName, outputFileName;
        VOSClusteringTechnique VOSClusteringTechnique;

        System.out.println("Modularity Optimizer version 1.3.0 by Ludo Waltman and Nees Jan van Eck");
        System.out.println();
        inputFileName = Config.LOUVAIN_INPUT;
        outputFileName = Config.LOUVAIN_OUTPUT;
        modularityFunction = Config.LOUVAIN_MODULARITY_FUNCTION;
        resolution = Config.LOUVAIN_RESOLUTION;
        algorithm = Config.LOUVAIN_ALGORITHM;
        nRandomStarts = Config.LOUVAIN_NO_RANDOM_STARTS;
        nIterations = Config.LOUVAIN_NO_ITERATIONS;
        randomSeed = Config.LOUVAIN_RANDOM_SEED;
        printOutput = (Config.LOUVAIN_PRINT_OUTPUT > 0);
        System.out.println();

        if (printOutput)
        {
            System.out.println("Reading input file...");
            System.out.println();
        }

        network = readInputFile(inputFileName, modularityFunction);

        if (printOutput)
        {
            System.out.format("Number of nodes: %d%n", network.getNNodes());
            System.out.format("Number of edges: %d%n", network.getNEdges());
            System.out.println();
            System.out.println("Running " + ((algorithm == 1) ? "Louvain algorithm" : ((algorithm == 2) ? "Louvain algorithm with multilevel refinement" : "smart local moving algorithm")) + "...");
            System.out.println();
        }

        resolution2 = ((modularityFunction == 1) ? (resolution / (2 * network.getTotalEdgeWeight() + network.totalEdgeWeightSelfLinks)) : resolution);

        beginTime = System.currentTimeMillis();
        clustering = null;
        maxModularity = Double.NEGATIVE_INFINITY;
        random = new Random(randomSeed);
        for (i = 0; i < nRandomStarts; i++)
        {
            if (printOutput && (nRandomStarts > 1))
                System.out.format("Random start: %d%n", i + 1);

            VOSClusteringTechnique = new VOSClusteringTechnique(network, resolution2);

            j = 0;
            update = true;
            do
            {
                if (printOutput && (nIterations > 1))
                    System.out.format("Iteration: %d%n", j + 1);

                if (algorithm == 1)
                    update = VOSClusteringTechnique.runLouvainAlgorithm(random);
                else if (algorithm == 2)
                    update = VOSClusteringTechnique.runLouvainAlgorithmWithMultilevelRefinement(random);
                else if (algorithm == 3)
                    VOSClusteringTechnique.runSmartLocalMovingAlgorithm(random);
                j++;

                modularity = VOSClusteringTechnique.calcQualityFunction();

                if (printOutput && (nIterations > 1))
                    System.out.format("Modularity: %.4f%n", modularity);
            }
            while ((j < nIterations) && update);

            if (modularity > maxModularity)
            {
                clustering = VOSClusteringTechnique.getClustering();
                maxModularity = modularity;
            }

            if (printOutput && (nRandomStarts > 1))
            {
                if (nIterations == 1)
                    System.out.format("Modularity: %.4f%n", modularity);
                System.out.println();
            }
        }
        endTime = System.currentTimeMillis();

        if (printOutput)
        {
            if (nRandomStarts == 1)
            {
                if (nIterations > 1)
                    System.out.println();
                System.out.format("Modularity: %.4f%n", maxModularity);
            }
            else
                System.out.format("Maximum modularity in %d random starts: %.4f%n", nRandomStarts, maxModularity);
            System.out.format("Number of communities: %d%n", clustering.getNClusters());
            System.out.format("Elapsed time: %d seconds%n", Math.round((endTime - beginTime) / 1000.0));
            System.out.println();
            System.out.println("Writing output file...");
            System.out.println();
        }

        writeOutputFile(outputFileName, clustering);
    }

    private static Network readInputFile(String fileName, int modularityFunction) throws IOException
    {
        BufferedReader bufferedReader;
        double[] edgeWeight1, edgeWeight2, nodeWeight;
        int i, j, nEdges, nLines, nNodes;
        int[] firstNeighborIndex, neighbor, nNeighbors, node1, node2;
        Network network;
        String[] splittedLine;

        bufferedReader = new BufferedReader(new FileReader(fileName));

        nLines = 0;
        while (bufferedReader.readLine() != null)
            nLines++;

        bufferedReader.close();

        bufferedReader = new BufferedReader(new FileReader(fileName));

        node1 = new int[nLines];
        node2 = new int[nLines];
        edgeWeight1 = new double[nLines];
        i = -1;
        for (j = 0; j < nLines; j++)
        {
            splittedLine = bufferedReader.readLine().split("\t");
            node1[j] = Integer.parseInt(splittedLine[0]);
            if (node1[j] > i)
                i = node1[j];
            node2[j] = Integer.parseInt(splittedLine[1]);
            if (node2[j] > i)
                i = node2[j];
            edgeWeight1[j] = (splittedLine.length > 2) ? Double.parseDouble(splittedLine[2]) : 1;
        }
        nNodes = i + 1;

        bufferedReader.close();

        nNeighbors = new int[nNodes];
        for (i = 0; i < nLines; i++)
            if (node1[i] < node2[i])
            {
                nNeighbors[node1[i]]++;
                nNeighbors[node2[i]]++;
            }

        firstNeighborIndex = new int[nNodes + 1];
        nEdges = 0;
        for (i = 0; i < nNodes; i++)
        {
            firstNeighborIndex[i] = nEdges;
            nEdges += nNeighbors[i];
        }
        firstNeighborIndex[nNodes] = nEdges;

        neighbor = new int[nEdges];
        edgeWeight2 = new double[nEdges];
        Arrays.fill(nNeighbors, 0);
        for (i = 0; i < nLines; i++)
            if (node1[i] < node2[i])
            {
                j = firstNeighborIndex[node1[i]] + nNeighbors[node1[i]];
                neighbor[j] = node2[i];
                edgeWeight2[j] = edgeWeight1[i];
                nNeighbors[node1[i]]++;
                j = firstNeighborIndex[node2[i]] + nNeighbors[node2[i]];
                neighbor[j] = node1[i];
                edgeWeight2[j] = edgeWeight1[i];
                nNeighbors[node2[i]]++;
            }

        if (modularityFunction == 1)
            network = new Network(nNodes, firstNeighborIndex, neighbor, edgeWeight2);
        else
        {
            nodeWeight = new double[nNodes];
            Arrays.fill(nodeWeight, 1);
            network = new Network(nNodes, nodeWeight, firstNeighborIndex, neighbor, edgeWeight2);
        }

        return network;
    }

    private static void writeOutputFile(String fileName, Clustering clustering) throws IOException
    {
        BufferedWriter bufferedWriter;
        int i, nNodes;

        nNodes = clustering.getNNodes();

        clustering.orderClustersByNNodes();

        bufferedWriter = new BufferedWriter(new FileWriter(fileName));

        for (i = 0; i < nNodes; i++)
        {
            bufferedWriter.write(i + " " + Integer.toString(clustering.getCluster(i)));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
    }
}
