package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentNetherBridgeStairs extends ComponentNetherBridgePiece
{
    public ComponentNetherBridgeStairs(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        coordBaseMode = par4;
        boundingBox = par3StructureBoundingBox;
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
    {
        getNextComponentZ((ComponentNetherBridgeStartPiece)par1StructureComponent, par2List, par3Random, 6, 2, false);
    }

    /**
     * Creates and returns a new component piece. Or null if it could not find enough room to place it.
     */
    public static ComponentNetherBridgeStairs createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -2, 0, 0, 7, 11, 7, par5);

        if (!isAboveGround(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentNetherBridgeStairs(par6, par1Random, structureboundingbox, par5);
        }
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 6, 1, 6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 0, 6, 10, 6, 0, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 0, 1, 8, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 5, 2, 0, 6, 8, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 1, 0, 8, 6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 6, 2, 1, 6, 8, 6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 2, 6, 5, 8, 6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, 2, 0, 5, 4, Block.netherFence.blockID, Block.netherFence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 6, 3, 2, 6, 5, 2, Block.netherFence.blockID, Block.netherFence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 6, 3, 4, 6, 5, 4, Block.netherFence.blockID, Block.netherFence.blockID, false);
        placeBlockAtCurrentPosition(par1World, Block.netherBrick.blockID, 0, 5, 2, 5, par3StructureBoundingBox);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 2, 5, 4, 3, 5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 3, 2, 5, 3, 4, 5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 2, 2, 5, 2, 5, 5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 2, 5, 1, 6, 5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 7, 1, 5, 7, 4, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 6, 8, 2, 6, 8, 4, 0, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 2, 6, 0, 4, 8, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 5, 0, Block.netherFence.blockID, Block.netherFence.blockID, false);

        for (int i = 0; i <= 6; i++)
        {
            for (int j = 0; j <= 6; j++)
            {
                fillCurrentPositionBlocksDownwards(par1World, Block.netherBrick.blockID, 0, i, -1, j, par3StructureBoundingBox);
            }
        }

        return true;
    }
}
